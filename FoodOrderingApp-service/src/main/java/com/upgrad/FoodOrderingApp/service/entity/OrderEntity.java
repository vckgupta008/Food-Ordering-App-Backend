package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class OrderEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "BILL")
    @NotNull
    private int bill;

    @OneToOne
    @JoinColumn(name = "COUPON_ID")
    private CouponEntity couponEntity;

    @Column(name = "DISCOUNT")
    private int discount;

    @Column(name = "DATE")
    @NotNull
    private ZonedDateTime date;

    @Column(name = "PAYMENT_ID")
    private Integer payment_id;

    @OneToMany
    @JoinColumn(name = "CUSTOMER_ID")
    @NotNull
    private CustomerEntity customerEntity;

//    @Column(name = "ADDRESS_ID")
//    private Integer Address_id;
//
//    @Column(name = "RESTAURANT_ID")
//    private Integer restaurant_id;

}
