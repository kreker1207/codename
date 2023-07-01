package com.app.wordgame.service;

import com.app.wordgame.entity.Card;
import com.app.wordgame.entity.CardType;

import com.app.wordgame.entity.DTO.RoomManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final List<String> words;

    public GameService(@Value("${game.words.filepath}") String filePath) throws IOException {
        this.words = loadWordsFromFile(filePath);

    }

    private static List<String> loadWordsFromFile(String filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    public List<Card> generateCards(int size) {
        List<Card> cards = new ArrayList<>(size * size);

        List<CardType> cardTypes = createCardTypesList();
        List<String> shuffledWords = new ArrayList<>(words);
        Collections.shuffle(shuffledWords);
        int i = 0;
        while (cards.size() < size * size) {
            CardType cardType = cardTypes.get(i % cardTypes.size());
            String word = shuffledWords.get(cards.size());
            cards.add(new Card(word, cardType));
            i++;
        }

        return cards;
    }

    private List<CardType> createCardTypesList() {
        List<CardType> cardTypes = new ArrayList<>(25);
        cardTypeList(cardTypes,1,CardType.BLACK);
        cardTypeList(cardTypes,9,CardType.ORANGE);
        cardTypeList(cardTypes,8,CardType.BLUE);
        cardTypeList(cardTypes,7,CardType.WHITE);
        Collections.shuffle(cardTypes);

        return cardTypes;
    }
    private List<CardType> cardTypeList(List<CardType> cardTypes, int size,CardType cardType){
        for (int i  = 0; i< size; i++){
            cardTypes.add(cardType);
        }
        return cardTypes;
    }

}