package com.glez.mng_service.controller;

import com.glez.mng_service.entities.BookEntity;
import com.glez.mng_service.services.MNGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mng")
@RequiredArgsConstructor
public class MNGController {

    private final MNGService mngService;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookEntity> getAllBooks() {
        return mngService.getAllBooks();
    }

    @GetMapping("/books/update-list")
    @ResponseStatus(HttpStatus.OK)
    public void updateTmo() {
        mngService.updateTMOBooks();
    }

    @GetMapping("/books/update/{id}")
    public void updateBook(@PathVariable UUID id) {
        this.mngService.updateBook(id);
    }
}
