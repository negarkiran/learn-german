package com.kiran.german.domain.word;

import com.kiran.german.api.requests.WordRequest;
import com.kiran.german.api.responses.WordResponse;

import java.util.List;

public interface WordRepository {
    WordResponse create(WordRequest wordRequest);
    WordResponse update(String id, WordRequest wordRequest);
    void delete(String id);
    long count();
    List<WordResponse> findAll();
    List<WordResponse> findByPage(int page, int size);
    List<WordResponse> findByWord(String word);
}
