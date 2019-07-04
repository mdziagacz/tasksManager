package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.api.key}")
    private String trelloAppKey;

    @Value("&{trello.api.token}")
    private String trelloApiToken;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards(){

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/maciejdziagacz/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloApiToken)
                .queryParam("fields", "name,id").build().encode().toUri();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url,TrelloBoardDto[].class);

        if (boardsResponse != null){
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();


    }
}
