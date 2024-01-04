package com.glez.mng_service.services;

import com.glez.common.utils.JsonUtils;
import com.glez.mng_service.entities.BookEntity;
import com.glez.mng_service.repository.BookRepository;
import com.glez.mng_service.repository.ChapterRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MNGService {

    private final BookRepository bookRepository;
    private final ChapterRepository chapterRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            while (true) {
                this.kafkaTemplate.send("tmo-scan-library-topic", "update-tmo-books");
                bookRepository.findAll().forEach(book -> {
                    LocalDateTime now = LocalDateTime.now();
                    if (book.getChapters().isEmpty()) {
                        this.kafkaTemplate.send("tmo-update-book", JsonUtils.toJson(book));
                    } else {
                        LocalDateTime lastUpdated = LocalDateTime.ofEpochSecond(book.getUpdatedAt().getTime(), 0, ZoneOffset.UTC);
                        if (now.minusDays(7).isAfter(lastUpdated)) {
                            this.kafkaTemplate.send("tmo-update-book", JsonUtils.toJson(book));
                        }
                    }
                });
                try {
                    TimeUnit.DAYS.sleep(1);
                } catch (InterruptedException e) {
                    log.error("Error filling queue: ", e);
                }
            }
        }).start();
    }

    public List<BookEntity> getAllBooks() {
        var books = this.bookRepository.findAll();
        books.forEach(book -> book.setChapters(this.chapterRepository.findAllByBookId(book.getId())));
        return books;
    }

    public void updateTMOBooks() {
        this.kafkaTemplate.send("tmo-scan-library-topic", "update-tmo-books");
    }

    public void updateBook(UUID id) {
        BookEntity book = this.bookRepository.findById(id).orElseThrow();
        book.setChapters(this.chapterRepository.findAllByBookId(book.getId()));
        if (book.getUrl().contains("lectortmo")) {
            this.kafkaTemplate.send("tmo-update-book", JsonUtils.toJson(book));
        }

    }
}
