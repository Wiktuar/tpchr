package ru.tpchr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tpchr.entities.Test;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {
}
