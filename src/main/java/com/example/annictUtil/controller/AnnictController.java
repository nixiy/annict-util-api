package com.example.annictUtil.controller;

import com.example.annictUtil.model.UserStatus;
import com.example.annictUtil.service.AnnictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnnictController {
    @Autowired
    AnnictService annictService;

    /**
     * @param username Annictのユーザ名
     * @param username
     * @param isMatch 検索対象と一致している作品を返す
     * @return
     */
    @GetMapping("/annict/{username}")
    public List<UserStatus> getUser(@PathVariable(value = "username") String username,
                                    @RequestParam(value = "is_match", defaultValue = "true") boolean isMatch) {
        return annictService.getUser(username, isMatch);
    }
}
