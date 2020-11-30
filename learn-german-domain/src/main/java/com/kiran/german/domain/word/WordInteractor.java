package com.kiran.german.domain.word;

import com.kiran.german.api.boundaries.WordRequestBoundary;
import com.kiran.german.api.requests.WordRequest;
import com.kiran.german.api.responses.WordMetadata;
import com.kiran.german.api.responses.WordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordInteractor implements WordRequestBoundary {

    @Autowired
    private WordRepository wordRepository;

    @Override
    public WordResponse createWord(WordRequest wordRequest) {
        return wordRepository.create(wordRequest);
    }

    @Override
    public WordResponse updateWord(String id, WordRequest wordRequest) {
        return wordRepository.update(id, wordRequest);
    }


    @Override
    public void deleteWord(String id) {
        wordRepository.delete(id);
    }

    @Override
    public List<WordResponse> getWord(String english) {
        return wordRepository.findByWord(english);
    }

    @Override
    public WordMetadata getAll(int page, int size) {
        List<WordResponse> allWords = wordRepository.findByPage(page, size);
        return new WordMetadata(getNumberOfPages(size), allWords);
    }

    private long getNumberOfPages(int size) {
        long total = wordRepository.count();
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
