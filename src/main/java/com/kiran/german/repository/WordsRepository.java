package com.kiran.german.repository;

import com.kiran.german.model.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordsRepository extends MongoRepository<Word, String> {
    List<Word> findByEnglishContainingIgnoreCase(String english, Sort sort);
}
