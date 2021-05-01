package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
     private CustomerService customerService;

    /**
     * RestController method called when the request pattern is of type "/order/coupon/{coupon_name}"
     * and the incoming request is of 'GET' type
     * Retrieve coupon details using coupon name
     *
     * @param authorization - String represents authorization token
     * @param couponName   - This represents coupon name
     * @return - ResponseEntity(CouponDetailsResponse, HttpStatus.OK)
     * @throws AuthorizationFailedException - if incorrect/ invalid authorization Token is sent,
     *                                      or the user has already signed out
     * @throws CouponNotFoundException - if incorrect/ invalid coupon name is sent,
     *      *                                      or the coupon name doesn't exist
     */
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getAllAnswersToQuestion(@RequestHeader("authorization") final String authorization,
                                                                         @PathVariable("coupon_name") String couponName)
            throws AuthorizationFailedException, CouponNotFoundException {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
        CouponEntity couponEntity = orderService.getCouponByCouponName(couponName);
        CouponDetailsResponse CouponDetailsResponse = new CouponDetailsResponse().id(UUID.fromString(couponEntity.getUuid()))
                .couponName(couponEntity.getCoupon_name()).percent(couponEntity.getPercent());

        return new ResponseEntity<com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse>(CouponDetailsResponse, HttpStatus.OK);
    }
}
