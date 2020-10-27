package com.kiran.german.dto;

import java.util.List;

public class WordMetadata {
    private long total;
    private List<WordDto> items;

    public WordMetadata(long total, List<WordDto> words) {
        this.total = total;
        this.items = words;
    }

    public long getTotal() {
        return total;
    }

    public List<WordDto> getItems() {
        return items;
    }
}
