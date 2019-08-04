package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {
    @InjectMocks
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "trelloList_test", false));
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("test_board", "1", trelloListDto));

        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertNotNull(trelloBoardList);
        trelloBoardList.forEach(trelloBoard -> {
            assertEquals("test_board", trelloBoard.getName());
            assertEquals("1", trelloBoard.getId());
            trelloBoard.getLists().forEach(trelloList -> {
                assertEquals("trelloList_test", trelloList.getName());
                assertEquals("1", trelloList.getId());
                assertFalse(trelloList.isClosed());
            });
        });
    }

    @Test
    public void testMapToBoardsDto() {
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "trelloList_test", false));
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("1", "test_board", trelloLists));

        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        assertNotNull(trelloBoardList);
        trelloBoardDtoList.forEach(trelloBoardDto -> {
            assertEquals("test_board", trelloBoardDto.getName());
            assertEquals("1", trelloBoardDto.getId());
            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("trelloList_test", trelloListDto.getName());
                assertEquals("1", trelloListDto.getId());
                assertFalse(trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "trelloList_test", false));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDto);

        //Then
        assertNotNull(trelloLists);
        trelloLists.forEach(trelloList -> {
            assertEquals("1", trelloList.getId());
            assertEquals("trelloList_test", trelloList.getName());
            assertFalse(trelloList.isClosed());
        });
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "trelloList_test", false));

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertNotNull(trelloLists);
        trelloListDtos.forEach(trelloListDto -> {
            assertEquals("1", trelloListDto.getId());
            assertEquals("trelloList_test", trelloListDto.getName());
            assertFalse(trelloListDto.isClosed());
        });
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test_card", "test", "top", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertNotNull(trelloCardDto);
        assertEquals("test_card", trelloCardDto.getName());
        assertEquals("test", trelloCardDto.getDescription());
        assertEquals("top", trelloCardDto.getPos());
        assertEquals("1", trelloCardDto.getListId());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_card", "test", "top", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertNotNull(trelloCard);
        assertEquals("test_card", trelloCard.getName());
        assertEquals("test", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }
}
