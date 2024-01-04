package com.glez.mng_service.repository;

import com.glez.common.entities.mng.Book;
import com.glez.mng_service.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the {@link Book} entity.
 */

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    Optional<BookEntity> findByTitle(String title);
}

