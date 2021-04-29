package com.upgrad.FoodOrderingApp.service.common;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class CommonValidation {

    @Autowired
    private CustomerDao customerDao;

    /**
     * Method to validate if the access token is not valid/ customer has already logged out/
     * the session has already expired
     *
     * @param accessToken - String represents access token
     * @return - CustomerAuthEntity if access token provided is valid one
     * @throws AuthorizationFailedException - if the access token is not valid/ customer has already logged out/
     *                                      the session has already expired
     */
    public CustomerAuthEntity validateCustomerAuthEntity(String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity authEntity = customerDao.getCustomerAuth(accessToken);

        // Throw exception if the customer is not logged in
        if (authEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }

        //Throw exception if the customer is already logged out
        if (authEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }

        final ZonedDateTime now = ZonedDateTime.now();
        // Throw exception is the customer session has already expired
        if (authEntity.getExpiresAt().isBefore(now)) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
        return authEntity;
    }
}
