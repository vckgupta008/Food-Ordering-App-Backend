package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * The CouponEntity class is mapped to table 'coupon' in database
 * All the columns are mapped to its respective attributes of the class
 */

@Entity
@Table(name = "coupon")
@NamedQueries(
        {
                @NamedQuery(name = "couponByCouponName",
                        query = "select c from CouponEntity c where c.couponName = :couponName"),
                @NamedQuery(name = "couponByUuid",
                        query = "select c from CouponEntity c where c.uuid = :uuid")
        }
)
public class CouponEntity implements Serializable {

    public CouponEntity() {
    }

    public CouponEntity(@NotNull String uuid, String coupon_name, @NotNull Integer percent) {
        this.uuid = uuid;
        this.couponName = coupon_name;
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
    @Size(max = 255)
    private String couponName;

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

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String coupon_name) {
        this.couponName = coupon_name;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CouponEntity that = (CouponEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(uuid, that.uuid)
                .append(couponName, that.couponName)
                .append(percent, that.percent)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(uuid)
                .append(couponName)
                .append(percent)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("uuid", uuid)
                .append("couponName", couponName)
                .append("percent", percent)
                .toString();
    }
}
