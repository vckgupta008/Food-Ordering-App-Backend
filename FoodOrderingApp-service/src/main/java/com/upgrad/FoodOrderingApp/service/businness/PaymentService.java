package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    /**
     * This method implements the business logic for 'payment' endpoint
     *
     * @return List<PaymentEntity> object
     */

    public List<PaymentEntity> getAllPaymentMethods() {
        return paymentDao.getAllPaymentMethods ();

    }
}
