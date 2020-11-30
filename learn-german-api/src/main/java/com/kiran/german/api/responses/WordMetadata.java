package com.kiran.german.api.responses;

import java.util.List;

public class WordMetadata {
    private long total;
    private List<WordResponse> items;

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
