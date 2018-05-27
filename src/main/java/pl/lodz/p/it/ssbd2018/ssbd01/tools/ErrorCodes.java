/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.tools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author michal
 */
public class ErrorCodes {

    public static final String UNKNOWN_ERROR = "unknown_error";
    public static final String SUCCESS = "success";
    public static final String AUTHENTICATION_ERROR = "authentication_error";
    public static final String USER_ALREADY_LOGIN = "user_already_login";
    public static final String USER_ALREADY_LOGOUT = "user_already_logout";
    public static final String UNAUTHORIZED = "user_is_not_authorized";
    public static final String NO_ID_SENT = "no_id_sent";
    public static final String POSTAL_CODE_LENGTH_ERROR = "postal_code_length_error";
    public static final String PASSWORD_LENGTH_ERROR = "password_length_error";
    public static final String PASSWORD_DIFFERENT_ERROR = "password_different_error";
    public static final String LOGIN_LENGTH_ERROR = "login_length_error";
    public static final String NAME_SYNTAX_ERROR = "name_syntax_error";
    public static final String NAME_LENGTH_ERROR = "name_length_error";
    public static final String SURNAME_SYNTAX_ERROR = "surname_syntax_error";
    public static final String SURNAME_LENGTH_ERROR = "surname_length_error";
    public static final String EMAIL_LENGTH_ERROR = "email_length_error";
    public static final String EMAIL_PATTERN_EXCEPTION = "email_pattern_error";
    public static final String PHONE_LENGTH_ERROR = "phone_length_error";
    public static final String PHONE_PATTERN_ERROR = "phone_pattern_error";
    public static final String STREET_SYNTAX_ERROR = "street_syntax_error";
    public static final String STREET_LENGTH_ERROR = "street_length_error";
    public static final String STREET_NUMBER_LENGTH_ERROR = "street_number_length_error";
    public static final String STREET_NUMBER_PATTERN_ERROR = "street_number_pattern_error";
    public static final String FLAT_NUMBER_LENGTH_ERROR = "flat_number_length_error";
    public static final String FLAT_NUMBER_PATTERN_ERROR = "flat_number_pattern_error";
    public static final String CITY_SYNTAX_ERROR = "city_syntax_error";
    public static final String CITY_LENGTH_ERROR = "city_length_error";
    public static final String COUNTRY_SYNTAX_ERROR = "country_syntax_error";
    public static final String COUNTRY_LENGTH_ERROR = "country_length_error";
    
    public List<String> getAllErrors() {
        List<String> errors = new ArrayList<>();
        errors.add(POSTAL_CODE_LENGTH_ERROR);
        errors.add(PASSWORD_LENGTH_ERROR);
        errors.add(PASSWORD_DIFFERENT_ERROR);
        errors.add(LOGIN_LENGTH_ERROR);
        errors.add(NAME_SYNTAX_ERROR);
        errors.add(NAME_LENGTH_ERROR);
        errors.add(SURNAME_SYNTAX_ERROR);
        errors.add(SURNAME_LENGTH_ERROR);
        errors.add(EMAIL_LENGTH_ERROR);
        errors.add(EMAIL_PATTERN_EXCEPTION);
        errors.add(PHONE_LENGTH_ERROR);
        errors.add(PHONE_PATTERN_ERROR);
        errors.add(STREET_SYNTAX_ERROR);
        errors.add(STREET_LENGTH_ERROR);
        errors.add(STREET_NUMBER_LENGTH_ERROR);
        errors.add(STREET_NUMBER_PATTERN_ERROR);
        errors.add(FLAT_NUMBER_LENGTH_ERROR);
        errors.add(FLAT_NUMBER_PATTERN_ERROR);
        errors.add(CITY_SYNTAX_ERROR);
        errors.add(CITY_LENGTH_ERROR);
        errors.add(COUNTRY_SYNTAX_ERROR);
        errors.add(COUNTRY_LENGTH_ERROR);
        return errors;
    }
    
}
