package com.digitalwin.exercicio.dto.walletDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    private String idPlayer;
    private Integer amount;
}
