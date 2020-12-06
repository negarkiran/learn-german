package com.kiran.german.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WordMetadata {
    private long total;
    private List<WordResponse> items;

    public WordMetadata(){

    }

    public WordMetadata(long total, List<WordResponse> words) {
        this.total = total;
        this.items = words;
    }

    public long getTotal() {
        return total;
    }

    public List<WordResponse> getItems() {
        return items;
    }
}
