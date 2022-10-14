package com.mina.drones.config;


import org.springframework.dao.DuplicateKeyException;

import java.util.Map;

public class ErrorMessages {

    public static final String ERROR_KEY = "Error";
    public static final Map<Class<? extends Exception>, String> EXCEPTIONS_MESSAGES = Map.of(
            DuplicateKeyException.class, "Duplicate drone serial number"
    );
}
