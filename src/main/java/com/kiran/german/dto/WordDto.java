package com.kiran.german.dto;

public class WordDto {

    private String id;

    private String english;

    private String german;

    public WordDto(String id, String english, String german) {
        this.id = id;
        this.english = english;
        this.german = german;
    }

    public String getId() {
        return id;
    }

    public String getEnglish() {
        return english;
    }

    public String getGerman() {
        return german;
    }
}
