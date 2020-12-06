package com.kiran.german.api.requests;

public class WordRequest {

    private String english;

    private String german;

    public WordRequest() {
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
        private String english;
        private String german;

        private Builder() {
        }

        public Builder withEnglish(String english) {
            this.english = english;
            return this;
        }

        public Builder withGerman(String german) {
            this.german = german;
            return this;
        }

        public WordRequest build() {
            WordRequest wordRequest = new WordRequest();
            wordRequest.german = this.german;
            wordRequest.english = this.english;
            return wordRequest;
        }
    }
}
