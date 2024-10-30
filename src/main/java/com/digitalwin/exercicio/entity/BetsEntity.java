package com.digitalwin.exercicio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BetsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idBet;
    @ManyToOne
    private PlayerEntity playerEntityId;
    private int number;
    private String result;
    @Builder.Default
    private LocalTime startBet = LocalTime.now();
    private LocalTime endBet;
}
