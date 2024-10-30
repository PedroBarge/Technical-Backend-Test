package com.digitalwin.exercicio.dto.gameDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndGameDto {
    private String idPlayer;
    private String idBet;
}
