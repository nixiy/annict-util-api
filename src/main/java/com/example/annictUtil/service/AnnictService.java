package com.example.annictUtil.service;

import annict.UserQuery;
import com.example.annictUtil.model.NodeDto;
import com.example.annictUtil.repository.AnnictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnictService {
    @Autowired
    AnnictRepository annictRepository;

    public List<NodeDto> getUser(String username) {
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
