package com.kiran.german.rest;

import com.kiran.german.api.boundaries.WordRequestBoundary;
import com.kiran.german.api.requests.WordRequest;
import com.kiran.german.api.responses.WordMetadata;
import com.kiran.german.api.responses.WordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("http://localhost:3000")
public class WordResource {

    @Autowired
    private WordRequestBoundary wordRequestBoundary;

    @GetMapping(value = "/words/{word}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WordResponse>> getWord(@PathVariable(value = "word") String word) {
        return new ResponseEntity<>(wordRequestBoundary.getWord(word), HttpStatus.OK);
    }

    @GetMapping(value = "/words")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<WordMetadata> getWords(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(wordRequestBoundary.getAll(page, size), HttpStatus.OK);
    }

    @PostMapping(value = "/words")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WordResponse> createWord(@RequestBody WordRequest wordRequest) {
        return new ResponseEntity<>(wordRequestBoundary.createWord(wordRequest), HttpStatus.OK);
    }

    @PutMapping(value = "/words/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WordResponse> updateWord(
            @PathVariable(value = "id") String id,
            @RequestBody WordRequest wordRequest) {
        return new ResponseEntity<>(wordRequestBoundary.updateWord(id, wordRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/words/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteWord(@PathVariable(value = "id") String id) {
        wordRequestBoundary.deleteWord(id);
        return ResponseEntity.noContent().build();
    }
}
