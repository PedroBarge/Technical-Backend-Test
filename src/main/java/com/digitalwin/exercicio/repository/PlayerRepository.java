package com.digitalwin.exercicio.repository;

import com.digitalwin.exercicio.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, String> {

    @Transactional
    @Modifying
    @Query("update PlayerEntity p set p.wallet = ?1 where p.idPlayer = ?2")
    int updateWalletByIdPlayer(Integer wallet, String idPlayer);
}
