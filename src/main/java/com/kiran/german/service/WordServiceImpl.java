package com.kiran.german.service;

import com.kiran.german.dto.WordDto;
import com.kiran.german.dto.WordMetadata;
import com.kiran.german.model.Word;
import com.kiran.german.repository.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService{

    @Autowired
    private WordsRepository wordsRepository;

    @Override
    public WordDto createWord(WordDto wordDto) {
        Word word = new Word(wordDto.getEnglish(), wordDto.getGerman());
        Word response = wordsRepository.save(word);
        return new WordDto(response.getId(), response.getEnglish(), response.getGerman());
    }

    @Override
    public WordDto updateWord(String id, WordDto wordDto) {
        Word word = wordsRepository.findById(id).orElseThrow(() -> new RuntimeException("Word Not Found"));
        word.setGerman(wordDto.getGerman());
        wordsRepository.save(word);
        return new WordDto(word.getId(), word.getEnglish(), word.getGerman());
    }

    @Override
    public void deleteWord(String id) {
        wordsRepository.deleteById(id);
    }

    @Override
    public List<WordDto> getWord(String english) {
        return wordsRepository.findByEnglishContainingIgnoreCase(english, Sort.by(Sort.Direction.ASC, "english")).stream()
                .map(word -> new WordDto(word.getId(), word.getEnglish(), word.getGerman()))
                .collect(Collectors.toList());
    }

    @Override
    public WordMetadata getAll(int page, int size) {
        List<WordDto> words = wordsRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "english")))
                .stream()
                .map(word -> new WordDto(word.getId(), word.getEnglish(), word.getGerman()))
                .collect(Collectors.toList());

        return new WordMetadata(getNumberOfPages(size), words);
    }

    private long getNumberOfPages(int size) {
        long total = wordsRepository.count();
        long pageCount = 0L;
        while(total / size > 0) {
            pageCount =  pageCount + (total / 10);
            total = total % 10;
        }
        if (total > 0) {
            pageCount++;
        }
        return pageCount;
    }
}
