package com.digitalwin.exercicio.dto.gameDto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayResponse {
    private String idPlayer;
    private String idBet;
    private LocalTime startBet;
    private LocalTime endBet;
    private int number;
    private String result;
}
