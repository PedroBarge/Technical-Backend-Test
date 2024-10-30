package com.digitalwin.exercicio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idPlayer;
    private Integer wallet;
    private String name;
}
