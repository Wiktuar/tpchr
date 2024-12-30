package ru.tpchr.repository.security;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tpchr.entities.security.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepo extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    @Modifying
    @Query("delete from PasswordResetToken p where p.author.id = :id")
    void deleteByAuthorId(@Param("id") long id);
}
