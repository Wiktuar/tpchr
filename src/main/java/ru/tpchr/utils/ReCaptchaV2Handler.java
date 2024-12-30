package ru.tpchr.utils;

import org.springframework.web.client.RestTemplate;
import ru.tpchr.DTO.CaptchaResponseDto;

import java.util.Collections;

public class ReCaptchaV2Handler {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private String secret = "6LfWK9snAAAAALTY2rbeb3KS0hMB8OAtzA-tdrx_";

    private RestTemplate restTemplate = new RestTemplate();

    public boolean verify(String recaptchaFormResponse) {
        System.out.println("secret key: " + secret);
        String url = String.format(CAPTCHA_URL, secret, recaptchaFormResponse);
        System.out.println("ReCaptcha v2 called.......");
        System.out.println("g-recaptcha-response: "+ recaptchaFormResponse);

        CaptchaResponseDto response =
                restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);


        System.out.println("ReCaptcha response: \n");
        System.out.println("Success: "+response.isSuccess());

        if (response.getErrorCodes() != null){
            System.out.println("Error codes: ");
            for (String errorCode: response.getErrorCodes()){
                System.out.println("\t" + errorCode);
            }
        }

        return response.isSuccess();
    }
}
