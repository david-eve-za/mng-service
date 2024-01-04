package com.glez.mng_service.repository;

import com.glez.common.entities.mng.Chapter;
import com.glez.mng_service.entities.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<ChapterEntity, UUID> {
    Set<Chapter> findAllByBookId(UUID id);
}
