package ru.tpchr.exceptions;

// это исключение возникает тогда, когда пользователь
// пытается зарегистрироваться с уже имеющимся в базе имейлом

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthorExistsException extends RuntimeException{
}

//https://zetcode.com/springboot/responsestatus/
// здесь можно посмотреть, как во фримаркере сделать страницу с ошибкой