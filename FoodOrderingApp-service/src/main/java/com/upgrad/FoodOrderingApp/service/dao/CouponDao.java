package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CouponDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get  coupon details based on the provided coupon name
     *
     * @param couponName - String that represents coupon name
     * @return - CouponEntity object if coupon exists for the provided coupon name, else return null
     */
    public CouponEntity getCouponByCouponName(final String couponName) {
        try {
            return entityManager.createNamedQuery("getCouponByCouponName", CouponEntity.class)
                    .setParameter("couponName", couponName)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
