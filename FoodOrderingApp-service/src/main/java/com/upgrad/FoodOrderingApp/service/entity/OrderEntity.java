package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * The OrderEntity class is mapped to table 'orders' in database
 * All the columns are mapped to its respective attributes of the class
 */

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {

    public OrderEntity() {
    }

    public OrderEntity(@Size(max = 200) @NotNull String uuid, @NotNull Double bill, CouponEntity coupon,
                       Double discount, @NotNull Date date, PaymentEntity payment,
                       @NotNull CustomerEntity customer, @NotNull AddressEntity address, @NotNull RestaurantEntity restaurant) {
        this.uuid = uuid;
        this.bill = bill;
        this.coupon = coupon;
        this.discount = discount;
        this.date = date;
        this.payment = payment;
        this.customer = customer;
        this.address = address;
        this.restaurant = restaurant;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "BILL")
    @NotNull
    private Double bill;

    @ManyToOne
    @JoinColumn(name = "COUPON_ID")
    private CouponEntity coupon;

    @Column(name = "DISCOUNT")
    private Double discount;

    @Column(name = "DATE")
    @NotNull
    private Date date;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @NotNull
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    @NotNull
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    @NotNull
    private RestaurantEntity restaurant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity couponEntity) {
        this.coupon = couponEntity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customerEntity) {
        this.customer = customerEntity;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
