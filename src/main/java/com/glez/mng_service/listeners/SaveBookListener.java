package com.glez.mng_service.listeners;

import com.glez.common.utils.JsonUtils;
import com.glez.mng_service.entities.BookEntity;
import com.glez.mng_service.entities.ChapterEntity;
import com.glez.mng_service.repository.BookRepository;
import com.glez.mng_service.repository.ChapterRepository;
import com.glez.mng_service.services.MNGService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SaveBookListener {

    private final BookRepository bookRepository;
    private final ChapterRepository chapterRepository;
    private final MNGService mngService;

    @KafkaListener(topics = "save-book-topic")
    public void handleSaveBook(String message) {
        BookEntity book = JsonUtils.fromJson(message, BookEntity.class);

        assert book != null;
        if (this.bookRepository.findByTitle(book.getTitle()).isEmpty()) {
            BookEntity save = this.bookRepository.save(book);
            this.mngService.updateBook(save.getId());
        }

        log.info("Received Messasge in group foo: {}", JsonUtils.prettyPrintJson(message));
    }

    @KafkaListener(topics = "save-chapter-topic")
    public void handleSaveChapter(String message) {
        ChapterEntity chapter = JsonUtils.fromJson(message, ChapterEntity.class);

        assert chapter != null;
        this.chapterRepository.save(chapter);

        log.info("Received Messasge in group foo: {}", JsonUtils.prettyPrintJson(message));
    }
}
