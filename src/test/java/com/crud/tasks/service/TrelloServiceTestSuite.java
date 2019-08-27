package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    TrelloService trelloService;
    @Mock
    TrelloClient trelloClient;
    @Mock
    SimpleEmailService simpleEmailService;
    @Mock
    AdminConfig adminConfig;


    @Test
    public void testCreatedTrelloCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test", "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "test",
                "test",
                "http://test.test",
                new BadgesDto(
                        1,
                        new AttachmentsByTypeDto(
                                new TrelloDto(
                                        1,
                                        1))));
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test@test");

        //When
        CreatedTrelloCardDto expected = trelloService.createdTrelloCard(trelloCardDto);

        //Then
        assertEquals(expected, createdTrelloCardDto);
        verify(simpleEmailService, times(1)).send(any(Mail.class));
    }

    @Test
    public void testFetchTrelloBoards(){
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "testTrelloListDto", false);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("testTrelloBoardDto", "1", trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> expexted = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(expexted, trelloBoardDtos);
    }
}
