package com.digitalwin.exercicio.controller;
import com.digitalwin.exercicio.dto.gameDto.EndGameDto;
import com.digitalwin.exercicio.dto.gameDto.PlayRequest;
import com.digitalwin.exercicio.dto.gameDto.PlayResponse;
import com.digitalwin.exercicio.dto.walletDto.WalletRequest;
import com.digitalwin.exercicio.dto.walletDto.WalletResponse;
import com.digitalwin.exercicio.entity.PlayerEntity;
import com.digitalwin.exercicio.service.PlayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dice")
@RestController
@RequiredArgsConstructor

public class StartController {

    private final PlayService service;

    @GetMapping("/createdefaultPlayer")
    public PlayerEntity defaultPlayer(){
        return service.defaultPlayer();
    }

    @PostMapping("/play")
    public ResponseEntity<PlayResponse> newPlay(@RequestBody PlayRequest playRequest){
        return service.startNewGame(playRequest);
    }
    @PostMapping("/endplay")
    public PlayResponse endPlay(@RequestBody EndGameDto endGameDto){
        return service.endGame(endGameDto);
    }

}
