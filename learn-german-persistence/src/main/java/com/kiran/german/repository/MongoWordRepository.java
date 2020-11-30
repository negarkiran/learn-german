package com.kiran.german.repository;

import com.kiran.german.api.requests.WordRequest;
import com.kiran.german.api.responses.WordResponse;
import com.kiran.german.domain.word.WordRepository;
import com.kiran.german.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MongoWordRepository implements WordRepository {

    @Autowired
    private final MongoOperations mongoOperations;

    public MongoWordRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public WordResponse create(WordRequest wordRequest) {
        Word word = new Word(wordRequest.getEnglish(), wordRequest.getGerman());
        Word savedWord = mongoOperations.save(word);
        return buildResponse(savedWord);
    }

    @Override
    public WordResponse update(String id, WordRequest wordRequest) {
        Word existingWord = mongoOperations.findById(id, Word.class);
        if (null != existingWord) {
            existingWord.setGerman(wordRequest.getGerman());
            Word savedWord = mongoOperations.save(existingWord);
            return buildResponse(savedWord);

        } else {
            throw new RuntimeException("Word Not Found");
        }
    }

    @Override
    public void delete(String id) {
        Query wordQuery = Query.query(Criteria.where("id").is(id));
        mongoOperations.remove(wordQuery, Word.class);
    }

    @Override
    public long count() {
        Query query = Query.query(Criteria.where("id").exists(true));
        return mongoOperations.count(query, Word.class);
    }

    @Override
    public List<WordResponse> findAll() {
        List<Word> allWords = mongoOperations.findAll(Word.class);
        return allWords.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<WordResponse> findByPage(int page, int size) {
        Query query = Query.query(Criteria.where("id")
                .exists(true))
                .with(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "english")));
        return mongoOperations.find(query, Word.class)
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<WordResponse> findByWord(String word) {
        Query query = Query.query(Criteria.where("english").regex(word));
        List<Word> words = mongoOperations.find(query, Word.class);
        return words.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    private WordResponse buildResponse(Word savedWord) {
        return WordResponse.builder()
                .withId(savedWord.getId())
                .withEnglish(savedWord.getEnglish())
                .withGerman(savedWord.getGerman())
                .build();
    }
}
