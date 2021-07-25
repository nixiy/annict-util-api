package com.example.annictUtil.controller;

import com.example.annictUtil.model.NodeDto;
import com.example.annictUtil.service.AnnictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnnictController {
    @Autowired
    AnnictService annictService;

    /**
     * AnnictAPIを叩いて、ユーザの作品ステータスを取得する
     * @param username Annictのユーザ名
     * @return
     */
    @GetMapping("/annict/{username}")
    public List<NodeDto> getUser(@PathVariable(value = "username") String username) {
        return annictService.getUser(username);
    }
}
