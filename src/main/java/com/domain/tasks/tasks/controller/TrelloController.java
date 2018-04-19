package com.domain.tasks.tasks.controller;

import com.domain.tasks.tasks.domain.CreateTrelloCard;
import com.domain.tasks.tasks.domain.TrelloBoardDto;
import com.domain.tasks.tasks.domain.TrelloCardDto;
import com.domain.tasks.tasks.service.TrelloService;
import com.domain.tasks.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {


    @Autowired
    private TrelloService trelloService;


    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloService.fetchTrelloBoards();
    }


    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreateTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloService.createTrelloCard(trelloCardDto);
    }
}
