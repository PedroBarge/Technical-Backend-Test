package com.digitalwin.exercicio.service.wallet;

import com.digitalwin.exercicio.dto.walletDto.WalletResponse;
import com.digitalwin.exercicio.entity.PlayerEntity;
import com.digitalwin.exercicio.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final PlayerRepository repository;
    public WalletResponse getWalletByPlayerId(String id) {
        Optional<PlayerEntity> playerEntity = repository.findById(id);

        return playerEntity.map(entity -> WalletResponse.builder().idPlayer(entity.getIdPlayer()).amount(entity.getWallet()).build()).orElse(null);
    }
}
