package com.kiran.german.model;

import org.springframework.data.annotation.Id;

public class Word {
    @Id
    private String id;

    private String english;

    private String german;

    public Word(String english, String german) {
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

    public void setGerman(String german) {
        this.german = german;
    }
}
