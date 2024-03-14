package com.vrpigroup.usermodule.constants;

import org.springframework.beans.factory.annotation.Value;

public class UserConstants {
    public static final String USER_NOT_FOUND = "User not found with username: ";
    public static final String USER_NAME = "userName";
    public static final String USER_ID = "User_ID";
    public static final String USER_NAME_COLUMN = "user_Name";
    public static final String USER_NAME_UNIQUE = "unique = true";
    public static final String USER_NAME_SIZE = "Name must be between 3 and 50 characters";
    public static final String PASSWORD = "Password";
    public static final String PASSWORD_SIZE = "Password must be at least 8 characters long";
    public static final String PASSWORD_ANNOTATION = "@Password";
    public static final String FULL_NAME = "Full_Name";
    public static final String FULL_NAME_SIZE = "Full Name must be between 3 and 50 characters";
    public static final String EMAIL = "Email";
    public static final String EMAIL_UNIQUE = "unique = true";
    public static final String EMAIL_VALID = "@ValidEmail";
    public static final String FATHERS_NAME = "Fathers_Name";
    public static final String FATHERS_NAME_SIZE = "Father's name must be between 3 and 50 characters";
    public static final String ADDRESS = "ADDRESS";
    public static final String ADDRESS_SIZE = "Address can't exceed 255 characters";
    public static final String PHONE_NUMBER = "Phone_Number";
    public static final String PHONE_NUMBER_UNIQUE = "unique = true";
    public static final String PHONE_NUMBER_SIZE = "Phone number must be between 3 and 50 characters";
    public static final String PHONE_NUMBER_VALID = "@Phone";
    public static final String PHONE_NUMBER_COLUMN = "Phone_Number";
    public static final String PHONE_NUMBER_SIZE_MESSAGE = "Phone number must be between 3 and 50 characters";
    public static final String PHONE_NUMBER_UNIQUE_MESSAGE = "Phone number already exists";
    public static final String PHONE_NUMBER_VALID_MESSAGE = "Phone number is not valid";
    public static final String USER_ID_COLUMN = "User_ID";
    public static final String USER_ID_GENERATED_VALUE = "strategy = GenerationType.IDENTITY";
    public static final String USER_ID_GENERATED_VALUE_COLUMN = "User_ID";
    public static final String USER_ID_GENERATED_VALUE_STRATEGY = "strategy = GenerationType.IDENTITY";
    public static final String USER_ID_GENERATED_VALUE_STRATEGY_COLUMN = "User_ID";

    //Status code and response message
    public static final String OK_MESSAGE = "OK";
    public static final String CREATED_MESSAGE = "Created";
    public static final String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
    public static final String FAILED_TO_PROCEED_LOGIN = "Failed to proceed with login";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String FAILED_TO_FETCH_ALL_USERS = "Failed to fetch all users";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found with username: ";
    public static final String FAILED_TO_CREATE_USER_ACCOUNT = "Failed to create user account for email: ";
    public static final String ERROR_WHILE_CREATING_USER_ACCOUNT = "Error while creating user account for email: ";
    public static final String GETTING_ALL_USERS = "Getting all users";
    public static final String GETTING_USER_BY_ID = "Getting";
    public static final String FAILED_TO_SEND_OTP = " Failed to send OTP to email: ";
    public static final String  FAILED_TO_VERIFY_ACCOUNT = "Failed to verify account";
    public static final String  ERROR_WHILE_VERIFYING_ACCOUNT = "Error while verifying account";
    public static final String  ACCOUNT_VERIFIED_SUCCESSFULLY = "Account verified successfully";
    public static final String  VERIFYING_ACCOUNT = "Verifying account";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    public static final String FAILED_TO_DELETE_USER = "Failed to delete user";
    public static final String  CREATING_USER_ACCOUNT = "Creating user account";
    public static final String  USER_ACCOUNT_CREATED_SUCCESSFULLY = "User account created successfully";
    public static final String  USER_WITH_USERNAME_OR_EMAIL_ALREADY_EXISTS = "User with username or email already exists. Cannot create a new user.";

    public static final String FAILED_TO_SEND_MESSAGE="Failed to send message";
    public static final String MESSAGE_SENT_SUCCESSFULLY="Message sent successfully";
    // status_code all
     // 501 Not Implemented server error response code means that the server does not support the functionality required to fulfill the request.
    public static final String HttpStatus_OK = "OK_200";
    public static final String CREATED_201 = "CREATED_201";
    public static final String ACCEPTED_202 = "ACCEPTED_202";
    public static final String NON_AUTHORITATIVE_INFORMATION_203 = "NON_AUTHORITATIVE_INFORMATION_203";
    public static final String NO_CONTENT_204 = "NO_CONTENT_204";
    public static final String RESET_CONTENT_205 = "RESET_CONTENT_205";
    public static final String PARTIAL_CONTENT_206 = "PARTIAL_CONTENT_206";
    public static final String MULTI_STATUS_207 = "MULTI_STATUS_207";
    public static final String ALREADY_REPORTED_208 = "ALREADY_REPORTED_208";
    public static final String IM_USED_226 = "IM_USED_226";
    public static final String MULTIPLE_CHOICES_300 = "MULTIPLE_CHOICES_300";
    public static final String MOVED_PERMANENTLY_301 = "MOVED_PERMANENTLY_301";
    public static final String FOUND_302 = "FOUND_302";
    public static final String SEE_OTHER_303 = "SEE_OTHER_303";
    public static final String NOT_MODIFIED_304 = "NOT_MODIFIED_304";
    public static final String USE_PROXY_305 = "USE_PROXY_305";
    public static final String SWITCH_PROXY_306 = "SWITCH_PROXY_306";
    public static final String TEMPORARY_REDIRECT_307 = "TEMPORARY_REDIRECT_307";
    public static final String PERMANENT_REDIRECT_308 = "PERMANENT_REDIRECT_308";
    public static final String BAD_REQUEST_400 = "BAD_REQUEST_400";
    public static final String UNAUTHORIZED_401 = "UNAUTHORIZED_401";
    public static final String PAYMENT_REQUIRED_402 = "PAYMENT_REQUIRED_402";
    public static final String FORBIDDEN_403 = "FORBIDDEN_403";
    public static final String NOT_FOUND_404 = "NOT_FOUND_404";
    public static final String METHOD_NOT_ALLOWED_405 = "METHOD_NOT_ALLOWED_405";
    public static final String NOT_ACCEPTABLE_406 = "NOT_ACCEPTABLE_406";
    public static final String INTERNAL_SERVER_ERROR_500 = "INTERNAL_SERVER_ERROR_500";
    public static final String NOT_IMPLEMENTED_501 = "NOT_IMPLEMENTED_501";
    public static final String BAD_GATEWAY_502 = "BAD_GATEWAY_502";
    public static final String SERVICE_UNAVAILABLE_503 = "SERVICE_UNAVAILABLE_503";
    public static final String GATEWAY_TIMEOUT_504 = "GATEWAY_TIMEOUT_504";
    public static final String HTTP_VERSION_NOT_SUPPORTED_505 = "HTTP_VERSION_NOT_SUPPORTED_505";
    public static final String VARIANT_ALSO_NEGOTIATES_506 = "VARIANT_ALSO_NEGOTIATES_506";
    public static final String INSUFFICIENT_STORAGE_507 = "INSUFFICIENT_STORAGE_507";
    public static final String LOOP_DETECTED_508 = "LOOP_DETECTED_508";

    @Value("${jwt.secret}")
    public static final String SECRET_KEY = "secret";
    public static final String PROFILE_PHOTO_UPLOADED_SUCCESSFULLY ="Profile photo uploaded successfully";
    public static final String FAILED_TO_UPLOAD_PROFILE_PHOTO ="Failed to upload profile photo";
    public static final String FAILED_TO_UPDATE_USER_DOCUMENTS = "Failed to update user documents";
    public static final String USER_DOCUMENTS_UPDATED_SUCCESSFULLY = "User documents updated successfully";
    ;
    ;
}
