package com.kiran.german.api.responses;

public class WordResponse {
    private String id;

    private String english;

    private String german;

    public WordResponse() {
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String english;
        private String german;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withEnglish(String english) {
            this.english = english;
            return this;
        }

        public Builder withGerman(String german) {
            this.german = german;
            return this;
        }

        public WordResponse build() {
            WordResponse wordResponse = new WordResponse();
            wordResponse.id = this.id;
            wordResponse.english = this.english;
            wordResponse.german = this.german;
            return wordResponse;
        }
    }
}
