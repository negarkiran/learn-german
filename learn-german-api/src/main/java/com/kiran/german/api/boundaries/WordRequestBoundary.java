package com.kiran.german.api.boundaries;


import com.kiran.german.api.requests.WordRequest;
import com.kiran.german.api.responses.WordMetadata;
import com.kiran.german.api.responses.WordResponse;

import java.util.List;

public interface WordRequestBoundary {
    WordResponse createWord(WordRequest wordRequest);
    WordResponse updateWord(String id, WordRequest wordRequest);
    void deleteWord(String id);
    List<WordResponse> getWord(String english);
    WordMetadata getAll(int page, int size);
}
