package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "coupon")
@NamedQueries(
        {
                @NamedQuery(name = "getCouponByCouponName",
                        query = "select c from CouponEntity c where c.coupon_name = :couponName"),
        }
)
public class CouponEntity implements Serializable {

    public CouponEntity() {
    }

    public CouponEntity(@NotNull String uuid, String coupon_name, @NotNull Integer percent) {
        this.uuid = uuid;
        this.coupon_name = coupon_name;
        this.percent = percent;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "COUPON_NAME")
    @Size(max = 200)
    private String coupon_name;

    @Column(name = "PERCENT")
    @NotNull
    private Integer percent;

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

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

}
