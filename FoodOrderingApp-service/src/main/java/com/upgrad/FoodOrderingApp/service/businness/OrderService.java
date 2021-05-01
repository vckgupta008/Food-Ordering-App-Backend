package com.upgrad.FoodOrderingApp.service.common;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CommonValidation commonValidation;

    /**
     * Get  coupon details based on the provided coupon name
     *
     * @return - CouponEntity, if coupon exist
     * @throws CouponNotFoundException -if incorrect/ invalid coupon name is sent,
     *      *      *                                      or the coupon name doesn't exist
     */
    public CouponEntity getCouponByCouponName(final String couponName)
            throws CouponNotFoundException {

        if (commonValidation.isEmptyFieldValue(couponName)) {
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        }
        CouponEntity couponEntity = couponDao.getCouponByCouponName(couponName);
        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }
        return couponEntity;
    }
}
