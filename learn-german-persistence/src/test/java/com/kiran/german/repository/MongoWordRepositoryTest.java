package com.kiran.german.repository;

import com.kiran.german.api.requests.WordRequest;
import com.kiran.german.api.responses.WordResponse;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.InetSocketAddress;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MongoWordRepositoryTest {

    private MongoTemplate mongoTemplate;

    private MongoClient client;

    private MongoServer server;

    private MongoWordRepository mongoWordRepository;

    @BeforeEach
    public void setUp() {
        server = new MongoServer(new MemoryBackend());
        InetSocketAddress serverAddress = server.bind();
        client = new MongoClient(new ServerAddress(serverAddress));
        mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(client, "test"));
        mongoWordRepository = new MongoWordRepository(mongoTemplate);
    }

    @Test
    public void shouldCreateAndFetchWord() {
        WordRequest wordRequest = WordRequest.builder()
                .withEnglish("Ten")
                .withGerman("Zehn")
                .build();
        mongoWordRepository.create(wordRequest);

        List<WordResponse> words = mongoWordRepository.findAll();
        assertEquals(1, words.size());
    }
}