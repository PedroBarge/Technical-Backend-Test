package com.digitalwin.exercicio.dto.gameDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayRequest {
    private String idPlayer;
    private int betAmount;
    private String type;  //Podia-se substituir-se por um ENUM talvez
}
