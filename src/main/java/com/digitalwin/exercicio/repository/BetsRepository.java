package com.digitalwin.exercicio.repository;

import com.digitalwin.exercicio.entity.BetsEntity;
import com.digitalwin.exercicio.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BetsRepository extends JpaRepository<BetsEntity, String> {
    Optional<BetsEntity> findByIdBetAndPlayerEntityId_IdPlayer(String idPlayer, String idBet);


    @Transactional
    @Modifying
    @Query("update BetsEntity b set b.endBet = ?1 where b.idBet = ?2 and b.playerEntityId = ?3")
    int updateEndGame(LocalTime endBet, String idBet, PlayerEntity playerEntityId);

    @Transactional
    @Modifying
    @Query("update BetsEntity b set b.result = ?1 where b.idBet = ?2 ")
    int updateResultByIdBet(String result, String idBet);

    List<BetsEntity> findByPlayerEntityId_IdPlayer(String idPlayer);
}
