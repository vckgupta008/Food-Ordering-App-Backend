package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method helps find all available payment methods
     *
     * @return List<PaymentEntity> object
     */

    //List all payment methods available in DB
    public List<PaymentEntity> getAllPaymentMethods() {
        try {
            return this.entityManager.createNamedQuery ( "allPaymentMethods" , PaymentEntity.class ).getResultList ();
        } catch (NoResultException nre) {
            return null;
        }
    }
}