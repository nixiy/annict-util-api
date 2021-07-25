package com.example.annictUtil.controller;

import annict.UserQuery;
import com.example.annictUtil.model.NodeDto;
import com.example.annictUtil.repository.AnnictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AnnictController {
    @Autowired
    AnnictRepository annictRepository;

    /**
     * AnnictAPIを叩いて、ユーザの作品ステータスを取得する
     * @param username Annictのユーザ名
     * @return
     */
    @GetMapping("/annict/{username}")
    public List<NodeDto> getUser(@PathVariable(value = "username") String username) {
        UserQuery.User user = annictRepository.getUser(username);
        List<NodeDto> nodes = new ArrayList<>();
        user.works().nodes().forEach(v -> {
            NodeDto nodeDto = new NodeDto();
            nodeDto.setTitle(v.title());
            nodeDto.setStatusState(v.viewerStatusState().rawValue());
            nodeDto.setOfficialSiteUrl(v.officialSiteUrl());
            nodes.add(nodeDto);
        });
        return nodes;
    }
}
