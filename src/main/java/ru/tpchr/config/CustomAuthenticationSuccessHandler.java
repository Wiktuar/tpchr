package ru.tpchr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import ru.tpchr.services.AuthorService;
import ru.tpchr.utils.HeaderMenuUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String targetUrl = null;
        if(session.getAttribute("SPRING_SECURITY_SAVED_REQUEST") != null){
            targetUrl = ((SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST")).getRedirectUrl();
        } else {
            targetUrl = "http://tpchr.ru/";
        }

        //  Получаем бины из контекста и обновляем бин со session scope
        AuthorService authorService = SpringContext.getBean(AuthorService.class);
        HeaderMenuUtil hmu = SpringContext.getBean(HeaderMenuUtil.class);
        hmu.setAuthorService(authorService);
        hmu.setPrincipalName(SecurityContextHolder.getContext().getAuthentication().getName());

        httpServletResponse.setStatus(200);
        httpServletResponse.getOutputStream()
                .println(objectMapper.writeValueAsString(targetUrl));
    }
}
