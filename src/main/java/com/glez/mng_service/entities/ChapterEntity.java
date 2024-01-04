package com.glez.mng_service.entities;

import com.glez.common.entities.mng.Chapter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "chapters")
public class ChapterEntity extends Chapter {
}
