package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Method to handle SignUpRestrictedException if the customer already exists in the database
     * with the given contact number during signup, or does not pass the validations
     *
     * @param exp    - SignUpRestrictedException
     * @param request - WebRequest
     * @return - ResponseEntity (ErrorResponse along with Http status code
     */
    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse> signUpRestrictionException(
            final SignUpRestrictedException exp, final WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exp.getCode())
                .message(exp.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to handle AuthenticationFailedException if invalid credentials are provided during login,
     * or does not pass the validations
     *
     * @param exp    - AuthenticationFailedException
     * @param request - WebRequest
     * @return - ResponseEntity (ErrorResponse along with Http status code
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFailedException(
            final AuthenticationFailedException exp, final WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exp.getCode())
                .message(exp.getErrorMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Method to handle AuthorizationFailedException if invalid/ expired authorization token is provided during logout,
     *
     * @param exp    - AuthorizationFailedException
     * @param request - WebRequest
     * @return - ResponseEntity (ErrorResponse along with Http status code
     */
    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException(
            final AuthorizationFailedException exp, final WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exp.getCode())
                .message(exp.getErrorMessage()), HttpStatus.FORBIDDEN);
    }

}
