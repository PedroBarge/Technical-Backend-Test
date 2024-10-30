package com.digitalwin.exercicio.service;

import com.digitalwin.exercicio.dto.gameDto.EndGameDto;
import com.digitalwin.exercicio.dto.gameDto.PlayRequest;
import com.digitalwin.exercicio.dto.gameDto.PlayResponse;
import com.digitalwin.exercicio.entity.BetsEntity;
import com.digitalwin.exercicio.entity.PlayerEntity;
import com.digitalwin.exercicio.repository.BetsRepository;
import com.digitalwin.exercicio.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PlayService {
    private final PlayerRepository repository;
    private final BetsRepository betsRepository;
    private final ResponseEntity<PlayResponse> ERROR_BAD_REQUEST = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    public PlayerEntity defaultPlayer() {
        return repository.save(PlayerEntity.builder().name("Pedro").wallet(1000).build());

    }

    public ResponseEntity<PlayResponse> startNewGame(PlayRequest playRequest) {
        int betValue = playRequest.getBetAmount();
        //--Verificar se o Player existe
        Optional<PlayerEntity> player = repository.findById(playRequest.getIdPlayer());
        if (player.isEmpty()) {
            return ERROR_BAD_REQUEST;
        }
        //--Verificar se existe ja bet com o id do Player e se ainda esta em aberto a bet

        List<BetsEntity> betIdWithPlayerId = betsRepository.findByPlayerEntityId_IdPlayer(player.get().getIdPlayer());
        //estava a ter problemas com o Optional. Por isso decidi usar uma List e depois passar por uma StreamApi

        if (betIdWithPlayerId.stream().anyMatch(bet -> bet.getEndBet() == null)) {
            return ERROR_BAD_REQUEST;
        }

        //--Verificar se tem saldo para fazer a aposta
        if (player.get().getWallet() < betValue) {
            return ERROR_BAD_REQUEST;
        }
        //--Nova bet
        BetsEntity newBet = createBet(player);
        int newValueWallet;
        if (newBet.getResult().toLowerCase().equals(playRequest.getType())) {
            //--Vitória
            newValueWallet = player.get().getWallet() + (betValue * 2);
            newBet.setResult("Won");
        } else {
            //--Derrota
            newValueWallet = player.get().getWallet() - betValue;
            newBet.setResult("Loose");
        }
        player.get().setWallet(newValueWallet);
        repository.updateWalletByIdPlayer(newValueWallet,player.get().getIdPlayer());
        String betResult = newBet.getResult().equals(playRequest.getType()) ? "Won" : "Loose"; //podia ter feito isto para ou outros
        betsRepository.updateResultByIdBet(betResult,newBet.getIdBet());

        return ResponseEntity.ok(PlayResponse.builder()
                .idBet(newBet.getIdBet())
                .idPlayer(player.get().getIdPlayer())
                .startBet(newBet.getStartBet())
                .number(newBet.getNumber())
                .result(newBet.getResult())
                .build());
    }

    private BetsEntity createBet(Optional<PlayerEntity> player) {
        Random random = new Random();
        int number = random.nextInt(6);
        String resultOddOrEven;
        if (number % 2 == 0) {
            resultOddOrEven = "even";
        } else resultOddOrEven = "odd";
        return betsRepository.save(BetsEntity.builder()
                .playerEntityId(player.get())
                .number(number)
                .result(resultOddOrEven)
                .build());
    }

    public PlayResponse endGame(EndGameDto endGameDto) {
        Optional<BetsEntity> bets = betsRepository.findByIdBetAndPlayerEntityId_IdPlayer(endGameDto.getIdBet(), endGameDto.getIdPlayer());
        if (bets.isPresent()) {
            LocalTime time = LocalTime.now();
            PlayerEntity player = repository.getReferenceById(endGameDto.getIdPlayer());
            bets.get().setEndBet(time);
            betsRepository.updateEndGame(time, endGameDto.getIdBet(), player);
            var info = bets.get();
            return PlayResponse.builder()
                    .idPlayer(info.getPlayerEntityId().getIdPlayer())
                    .idBet(info.getIdBet())
                    .startBet(info.getStartBet())
                    .endBet(info.getEndBet())
                    .number(info.getNumber())
                    .result(info.getResult())
                    .build();
        }
        return null;
    }
}
