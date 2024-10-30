package com.digitalwin.exercicio.service;

import com.digitalwin.exercicio.dto.gameDto.EndGameDto;
import com.digitalwin.exercicio.dto.gameDto.PlayRequest;
import com.digitalwin.exercicio.dto.gameDto.PlayResponse;
import com.digitalwin.exercicio.dto.walletDto.WalletRequest;
import com.digitalwin.exercicio.dto.walletDto.WalletResponse;
import com.digitalwin.exercicio.entity.BetsEntity;
import com.digitalwin.exercicio.entity.PlayerEntity;
import com.digitalwin.exercicio.repository.BetsRepository;
import com.digitalwin.exercicio.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
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

    public PlayerEntity defaultPlayer() {
        return repository.save(PlayerEntity.builder().name("Pedro").wallet(1000).build());

    }

    public PlayResponse startNewGame(PlayRequest playRequest) {
        int betValue = playRequest.getBetAmount();
        /*TODO; 1- verificar se o jogador ja existe em alguma aposta
                2- Se existir devolver erro
                3- Se não criar nova aposta com o Id do player
                4- Fazer aposta
        */
        Optional<PlayerEntity> player = repository.findById(playRequest.getIdPlayer());
        Optional<BetsEntity> betIdWithPlayerId = betsRepository.findByPlayerEntityId_IdPlayer(player.get().getIdPlayer());
        //Verificar se existe ja bet com o id do Player
        if (betIdWithPlayerId.isPresent() && (betIdWithPlayerId.get().getEndBet() == null)) {
            return null;
        }
        //--
        BetsEntity newBet = createBet(player);
        if (newBet.getResult().equals(playRequest.getType())) {
            repository.updateWalletByIdPlayer((player.get().getWallet() + (betValue * 2)), player.get().getIdPlayer());
        } else repository.updateWalletByIdPlayer((player.get().getWallet() - betValue), player.get().getIdPlayer());

        return PlayResponse.builder()
                .idBet(newBet.getIdBet())
                .idPlayer(player.get().getIdPlayer())
                .startBet(newBet.getStartBet())
                .number(newBet.getNumber())
                .result(newBet.getResult())
                .build();
    }

    private BetsEntity createBet(Optional<PlayerEntity> player) {
        Random random = new Random();
        int number = random.nextInt();
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
            betsRepository.updateEndBetByIdBetAndPlayerEntityId(time, endGameDto.getIdBet(), player);
            return PlayResponse.builder()
                    .idPlayer(bets.get().getPlayerEntityId().getIdPlayer())
                    .idBet(bets.get().getIdBet())
                    .startBet(bets.get().getStartBet())
                    .endBet(time)
                    .result(bets.get().getResult())
                    .build();
        }
        return null;
    }
}
