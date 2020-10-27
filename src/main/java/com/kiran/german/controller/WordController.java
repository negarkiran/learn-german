package com.kiran.german.controller;

import com.kiran.german.dto.WordDto;
import com.kiran.german.dto.WordMetadata;
import com.kiran.german.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("http://localhost:3000")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping(value = "/words/{word}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WordDto>> getWord(@PathVariable(value = "word") String word) {
        return new ResponseEntity<List<WordDto>>(wordService.getWord(word), HttpStatus.OK);
    }

    @GetMapping(value = "/words")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<WordMetadata> getWords(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(wordService.getAll(page, size), HttpStatus.OK);
    }

    @PostMapping(value = "/words")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WordDto> createWord(@RequestBody WordDto wordDto) {
        return new ResponseEntity<>(wordService.createWord(wordDto), HttpStatus.OK);
    }

    @PutMapping(value = "/words/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WordDto> updateWord(@PathVariable(value = "id") String id, @RequestBody WordDto wordDto) {
        return new ResponseEntity<>(wordService.updateWord(id, wordDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/words/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteWord(@PathVariable(value = "id") String id) {
        wordService.deleteWord(id);
        return ResponseEntity.noContent().build();
    }
}
