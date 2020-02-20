package com.mimi.mlibrary.dao;

import com.mimi.mlibrary.model.works.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {
}