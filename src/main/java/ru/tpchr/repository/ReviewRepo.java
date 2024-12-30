package ru.tpchr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tpchr.entities.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
}
