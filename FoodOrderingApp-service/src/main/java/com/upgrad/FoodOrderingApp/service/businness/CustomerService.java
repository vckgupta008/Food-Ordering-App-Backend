package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#@$%&*!^]).{8,}$";

    /**
     * Method to save customer information once it has passed all validations
     *
     * @param customerEntity - CustomerEntity to be persisted in the database
     * @return - persisted CustomerEntity
     * @throws SignUpRestrictedException - if the required field information is missing, or does not pass validations,
     *                                   or customer with contact number already exists in the database
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {

        // Throw exception if email Id pattern does not match the pattern
        boolean isValidEmail = isValidPattern(EMAIL_PATTERN, customerEntity.getEmail());
        if (!isValidEmail) {
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        }

        // Throw exception if the contact number is not valid
        if (customerEntity.getContactNumber().length() > 10
                || customerEntity.getContactNumber().length() < 10
                || !StringUtils.isNumeric(customerEntity.getContactNumber())) {
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
        }

        // Throw exception if password does not match the pattern
        if (customerEntity.getPassword().length() < 8
                || !isValidPattern(PASSWORD_PATTERN, customerEntity.getPassword())) {
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        }

        // Throw exception if customer record exists in the database with the given contact number
        CustomerEntity custEntityByPhnNum = customerDao.getCustomerByContactNum(customerEntity.getContactNumber());
        if (custEntityByPhnNum != null) {
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number.");
        }

        String[] encryptedText = cryptographyProvider.encrypt(customerEntity.getPassword());
        customerEntity.setSalt(encryptedText[0]);
        customerEntity.setPassword(encryptedText[1]);

        return customerDao.saveCustomer(customerEntity);

    }

    private boolean isValidPattern(String reqPattern, String field) {
        Pattern pattern = Pattern.compile(reqPattern);
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }
}
