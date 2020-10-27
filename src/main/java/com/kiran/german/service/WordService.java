package com.kiran.german.service;

import com.kiran.german.dto.WordDto;
import com.kiran.german.dto.WordMetadata;

import java.util.List;

public interface WordService {
    WordDto createWord(WordDto wordDto);
    WordDto updateWord(String id, WordDto wordDto);
    void deleteWord(String id);
    List<WordDto> getWord(String english);
    WordMetadata getAll(int page, int size);
}
