package ru.tpchr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tpchr.entities.poem.Content;

@Repository
public interface ContentRepo extends JpaRepository<Content, Long> {
}
