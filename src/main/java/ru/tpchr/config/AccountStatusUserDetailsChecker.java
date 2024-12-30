package ru.tpchr.config;


import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

// класс обрабатывает ситуации, когда пользователь неактивен или заблокирован
// и выводит соответствующие сообщения
public class AccountStatusUserDetailsChecker implements UserDetailsChecker {

    @Override
    public void check(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            System.out.println(!userDetails.isEnabled());
            System.out.println("аккаунт не активирован ");
            throw new DisabledException("User is disabled");
        }

        if (!userDetails.isAccountNonLocked()) {
            System.out.println(!userDetails.isAccountNonLocked());
            System.out.println("Вы заблокированы администрацией сайта");
            throw new LockedException("Your account is blocked");
        }

//        if (!userDetails.isAccountNonExpired()) {
//            this.logger.debug("Failed to authenticate since user account is expired");
//            throw new AccountExpiredException(
//                    this.messages.getMessage("AccountStatusUserDetailsChecker.expired", "User account has expired"));
//        }
//        if (!userDetails.isCredentialsNonExpired()) {
//            this.logger.debug("Failed to authenticate since user account credentials have expired");
//            throw new CredentialsExpiredException("Пароль просрочен");
//        }
    }
}
