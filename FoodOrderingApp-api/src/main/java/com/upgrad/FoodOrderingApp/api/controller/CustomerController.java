package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * RestController method called when the request pattern is of type '/customer/signup'
     * and the incoming request is of 'POST' type
     * Persists SignupCustomerRequest details in the database
     *
     * @param signupCustomerRequest - signup customer details
     * @return - ResponseEntity (SignupCustomerResponse along with HTTP status code)
     * @throws SignUpRestrictedException - if the required field information is missing, or does not pass validations,
     *                                  or customer with contact number already exists in the database
     */
    @RequestMapping(method = RequestMethod.POST, path = "/customer/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signup(
            @RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest)
            throws SignUpRestrictedException {

        // Throw exception if any of the mandatory field value does not exist
        if (signupCustomerRequest.getFirstName().isEmpty()
                || signupCustomerRequest.getEmailAddress().isEmpty()
                || signupCustomerRequest.getContactNumber().isEmpty()
                || signupCustomerRequest.getPassword().isEmpty()) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        }

        // Set CustomerEntity fields using SignupCustomerRequest object
        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setPassword(signupCustomerRequest.getPassword());

        customerService.saveCustomer(customerEntity);

        SignupCustomerResponse customerResponse = new SignupCustomerResponse()
                .id(customerEntity.getUuid())
                .status("CUSTOMER SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SignupCustomerResponse>(customerResponse, HttpStatus.CREATED);
    }

}
