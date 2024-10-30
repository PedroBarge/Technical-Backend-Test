package com.digitalwin.exercicio.controller;

import com.digitalwin.exercicio.dto.walletDto.WalletResponse;
import com.digitalwin.exercicio.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/wallet")
@RestController
@RequiredArgsConstructor
public class WalletController {
    private final WalletService service;
    @GetMapping()
    public WalletResponse getWalletByPlayerId(@RequestParam String id){
        return service.getWalletByPlayerId(id);
    }
}
