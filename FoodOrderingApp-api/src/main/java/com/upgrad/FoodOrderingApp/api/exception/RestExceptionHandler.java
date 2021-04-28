package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
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
            SignUpRestrictedException exp, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse().code(exp.getCode())
                .message(exp.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

}
