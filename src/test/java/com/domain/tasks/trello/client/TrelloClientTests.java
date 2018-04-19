package com.domain.tasks.trello.client;

import com.domain.tasks.tasks.config.TrelloConfig;
import com.domain.tasks.tasks.domain.CreateTrelloCard;
import com.domain.tasks.tasks.domain.TrelloBoardDto;
import com.domain.tasks.tasks.domain.TrelloCardDto;
import com.domain.tasks.tasks.domain.TrelloListDto;
import com.domain.tasks.tasks.domain.badges.AttachmentsByType;
import com.domain.tasks.tasks.domain.badges.Badges;
import com.domain.tasks.tasks.domain.badges.Trello;
import com.domain.tasks.tasks.trello.client.TrelloClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTests {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_board", "test_id", new ArrayList<>());
        URI uri = new URI("http://test.com/members/mikolajbaranowicz/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreateTrelloCard createTrelloCard = new CreateTrelloCard(
                "1",
                "Test task",
                "http://test.com",
                new Badges(0, new AttachmentsByType(new Trello(0, 0)))
        );

        when(restTemplate.postForObject(uri, null, CreateTrelloCard.class)).thenReturn(createTrelloCard);

        CreateTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        List<TrelloListDto> list = new ArrayList<>();
        trelloBoards[0] = new TrelloBoardDto("test_board", "test_id", list);

        URI uri = new URI("http://test.com/members/null/boards?key=test&token=test&fields=id&fields=name&lists=all");

        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        assertEquals(0, fetchedTrelloBoards.size());
    }
}
