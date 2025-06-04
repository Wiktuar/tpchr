package ru.tpchr.repository.security;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tpchr.DTO.AuthorCabinetDTO;
import ru.tpchr.DTO.AuthorDTO;
import ru.tpchr.entities.security.Author;
import ru.tpchr.entities.security.Status;

import java.util.List;


@Repository
public interface AuthorRepo extends CrudRepository<Author, Long> {
    Author findByEmail(String email);
    Author findByActivationCode(String code);

//  метод получения всех авторов
    @Query("select new ru.tpchr.DTO.AuthorDTO(a.id, a.firstName, a.lastName, a.pathToAvatar) from Author a")
    List<AuthorDTO> getAllAuthors();

    @Query("select new ru.tpchr.DTO.AuthorDTO(a.firstName, a.lastName, a.pathToAvatar) " +
            "from Author a WHERE a.email = :email")
    AuthorDTO getAuthorDTOByEmail(@Param("email") String email);

//  метод полуxения cabinetAuthorDTO для общедоступной страницы автора
    @Query("select new ru.tpchr.DTO.AuthorCabinetDTO(a.id, a.firstName, a.lastName, a.pathToAvatar, " +
            "a.description, a.vk, a.tg, a.yt, a.rt) " +
            "from Author a WHERE a.id = :id")
    AuthorCabinetDTO getAuthorDTOById(@Param("id") long id);

    @Query("SELECT a.id from Author a WHERE a.email = :email")
    long getAuthorId(@Param("email") String email);

    //  если значение поля обновляется, то обязптельна аннотация @Modifying
    @Modifying
    @Query("UPDATE Author a SET a.password = :pass WHERE a.id = :id")
    void updateAuthor(@Param("pass") String password, @Param("id") long id);

    @Modifying
    @Query("UPDATE Author a SET a.firstName = :firstName, a.lastName = :lastName," +
            "a.pathToAvatar = :pathToAvatar, a.description = :description," +
            "a.tg = :tg, a.vk = :vk, a.yt = :yt, a.rt = :rt WHERE a.id = :id")
    void editAuthor(@Param("firstName") String firstName,
                    @Param("lastName") String lastName,
                    @Param("pathToAvatar") String pathToAvatar,
                    @Param("description") String description,
                    @Param("tg") String tg,
                    @Param("vk") String vk,
                    @Param("yt") String yt,
                    @Param("rt") String rt,
                    @Param("id") long id);

    //  обновление почты, кода активации и блокировка личного кабинета при изменении адреса почты
    @Modifying
    @Query("UPDATE Author a SET a.email = :email, a.pathToAvatar = :pathToAvatar, a.activationCode = :activationCode, " +
            "a.status = :status WHERE a.id = :id")
    void updateAuthorEmail(@Param("email") String email,
                           @Param("pathToAvatar") String pathToAvatar,
                           @Param("activationCode") String activationCode,
                           @Param("status")Status status,
                           @Param("id") long id);

    @Override
    void deleteById(Long id);

    @Query("select distinct new ru.tpchr.DTO.AuthorDTO(a.id, a.firstName, a.lastName, a.pathToAvatar) from Author a where size(a.compositions) > 0")
    List<AuthorDTO> getOnlyAuthors();
}
