package ru.tpchr.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.tpchr.DTO.AuthorDTO;
import ru.tpchr.services.AuthorService;
// класс для обработкиличного кабинета или кнопок авторизации в хэдере сайта

@Setter
@Getter
@Component
@SessionScope
public class HeaderMenuUtil {
    private AuthorDTO authorDTO;
    private String principalName;
    private AuthorService authorService;

    public HeaderMenuUtil(AuthorService authorService) {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!principalName.equals("anonymousUser")){;
            this.principalName = principalName;
            this.authorDTO = authorService.getAuthorDTOByEmail(this.principalName);
        } else {
            this.principalName = "default";
        }
    }


    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
        this.authorDTO = authorService.getAuthorDTOByEmail(this.principalName);
    }
}
