package com.domain.tasks.tasks.service;

import com.domain.tasks.tasks.config.AdminConfig;
import com.domain.tasks.tasks.domain.CreateTrelloCard;
import com.domain.tasks.tasks.domain.Mail;
import com.domain.tasks.tasks.domain.TrelloBoardDto;
import com.domain.tasks.tasks.domain.TrelloCardDto;
import com.domain.tasks.tasks.trello.client.TrelloClient;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Task: New Trello card";

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private AdminConfig adminConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreateTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
        val newCard = trelloClient.createNewCard(trelloCardDto);

        ofNullable(newCard).ifPresent(card -> emailService.send(new Mail(
                adminConfig.getAdminMail(), SUBJECT,
                "New card: " + card.getName() + "has been created on your Trello account"
        )));
        return newCard;
    }
}
