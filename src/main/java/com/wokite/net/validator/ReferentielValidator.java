package com.wokite.net.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReferentielValidator {

    private static final String REGEX_PHONE = "^[0-9]{8,15}$";
    private static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

}