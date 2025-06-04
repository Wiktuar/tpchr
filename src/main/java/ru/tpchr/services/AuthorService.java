package ru.tpchr.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tpchr.DTO.AuthorCabinetDTO;
import ru.tpchr.DTO.AuthorDTO;
import ru.tpchr.entities.Composition;
import ru.tpchr.entities.security.*;
import ru.tpchr.exceptions.AuthorExistsException;
import ru.tpchr.exceptions.TokenExistsException;
import ru.tpchr.repository.security.AuthorRepo;
import ru.tpchr.repository.security.PasswordResetTokenRepo;
import ru.tpchr.utils.Utils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class AuthorService implements UserDetailsService {

    private AuthorRepo authorRepo;
    private PasswordResetTokenRepo passwordResetTokenRepo;
    private PasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;
    private ComposeService composeService;

    @Autowired
    public void setAuthorRepo(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Autowired
    public void setPasswordTokenRepo(PasswordResetTokenRepo passwordResetTokenRepo) {
        this.passwordResetTokenRepo = passwordResetTokenRepo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @Autowired
    public void setComposeService(ComposeService composeService) {
        this.composeService = composeService;
    }

    //метод преобразования автора в пользователя Spring Security
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Author author = authorRepo.findByEmail(email);

        if (author == null) {
            throw new UsernameNotFoundException(String.format(" User %s is not exists!", email));
        }

        return new User(
                author.getEmail(), author.getPassword(),
                author.isActive(),
                author.isActive(),
                author.isActive(),
                author.isBlock(),
                Utils.mapRoleToAuthority(author.getRoles())
        );
    }

    //метод сохранения пользователя после регистрации
    @Transactional
    public boolean saveAuthor(Author author) {
        author.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setActivationCode(UUID.randomUUID().toString());
        author.setStatus(Status.NO_ACTIVE);
        author.setBlock(Block.NO_BANNED);
        authorRepo.save(author);
        mailSenderService.sendEmail(author.getEmail(), author.getFirstName(), author.getActivationCode());
        return true;
    }

//  метод сохранения отредактированного автора
    @Transactional
    public boolean saveEditAuthor(Author author){
        authorRepo.editAuthor(author.getFirstName(),
                              author.getLastName(),
                              author.getPathToAvatar(),
                              author.getDescription(),
                              author.getTg(),
                              author.getVk(),
                              author.getYt(),
                              author.getRt(),
                              author.getId());
        return true;
    }

//  метод обновляющий электронную почту автора
    @Transactional
    public void updateAuthorEmail(Author author){
        if(this.getAuthorByEmail(author.getEmail()) != null ) throw new AuthorExistsException();

//   изменение пути до аватарки
        Pattern p = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+");
        Matcher m = p.matcher(author.getPathToAvatar());
        author.setPathToAvatar(m.replaceFirst(author.getEmail()));

//   изменение пути для адресов обложек произведений автора
        List<Composition> allCompose = composeService.findAllByAuthorId(author.getId());
        allCompose.forEach(c -> Utils.changeFileName(c, p, author.getEmail()));
        composeService.saveAllCompositions(allCompose);

        String code = UUID.randomUUID().toString();
        authorRepo.updateAuthorEmail(author.getEmail(), author.getPathToAvatar(), code, Status.NO_ACTIVE, author.getId());
        mailSenderService.sendConfirmEmail(author.getEmail(), author.getFirstName(), code);
    }

//  метод, обновляющий пароль пользователя по его ID
    @Transactional
    public void updateAuthorById(String password, long id) {
        password = passwordEncoder.encode(password);
        passwordResetTokenRepo.deleteByAuthorId(id);
        authorRepo.updateAuthor(password, id);
    }

    // активация пользователя по коду активации
    public boolean activateAuthor(String code) {
        Author author = authorRepo.findByActivationCode(code);

        if (author == null)
            return false;

        author.setActivationCode(null);
        author.setStatus(Status.ACTIVE);
        authorRepo.save(author);

        return true;
    }

    // повторная активация пользователя по коду активации
    public void getRepeatActivationEmail(Author author){
        author.setActivationCode(UUID.randomUUID().toString());
        authorRepo.save(author);
        mailSenderService.sendEmail(author.getEmail(), author.getFirstName(), author.getActivationCode());
    }

//    метод получения автора по почте. Необходим для проверки в контроллере
//    на наличие уже зарегистрированных пользователей
//    @Cacheable("author")
    public Author getAuthorByEmail(String email) {
        log.warn("getting user by email: {}", email);
        return authorRepo.findByEmail(email);
    }

//  метод создания токена для сброса пароля
    public void createResetPassToken(Author author) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, author);
        mailSenderService.sendNewPassword(author.getEmail(), author.getFirstName(), token);
        passwordResetTokenRepo.save(passwordResetToken);
    }

//  метод, вовзращающий автора по токену для смены пароля
    @Transactional
    public PasswordResetToken getPasswordResetToken(String token){
         PasswordResetToken prt = passwordResetTokenRepo.findByToken(token);
         if(prt == null) throw new TokenExistsException("Автор не найден. Запросите новую ссылку " +
                 "восстановления пароля или обратитесь в техническую поддержку.");
        return prt;
    }

//  метод получения ID автора по его email
    public Long getAuthorId(String email){
        return authorRepo.getAuthorId(email);
    }

//  метод получения всех авторов
    public List<AuthorDTO> getAllAuthors(){
        List<AuthorDTO> allAuthors = authorRepo.getAllAuthors();
        allAuthors.sort(new Comparator<AuthorDTO>() {
            @Override
            public int compare(AuthorDTO o1, AuthorDTO o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });

        return allAuthors;
    }

//  метод получения AuthorDTO по его почте
    public AuthorDTO getAuthorDTOByEmail(String email){
        return authorRepo.getAuthorDTOByEmail(email);
    }

//  метод получения authorCabinetDTO lля общедоступной страницы автора
    public AuthorCabinetDTO getAuthorCabinetDTO(long id){
        return authorRepo.getAuthorDTOById(id);
    }

//  метод удаления автора по его ID
    public void deleteAuthorById(long id){
        authorRepo.deleteById(id);
    }

//  метод получения только тех авторов, у которых есть стихотворения
    public List<AuthorDTO> getOnlyAuthors(){
        return authorRepo.getOnlyAuthors();
    }
}

