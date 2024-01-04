package com.glez.mng_service.entities;

import com.glez.common.entities.mng.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity extends Book {
}
