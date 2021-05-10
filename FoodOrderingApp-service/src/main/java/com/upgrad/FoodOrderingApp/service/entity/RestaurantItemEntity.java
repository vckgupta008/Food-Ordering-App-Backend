package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The RestaurantItemEntity class is mapped to table 'restaurant_item' in database
 * All the columns are mapped to its respective attributes of the class
 */

@Entity
@Table(name = "restaurant_item")
public class RestaurantItemEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ITEM_ID")
    private ItemEntity item;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "RESTAURANT_ID")
    private RestaurantEntity restaurant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RestaurantItemEntity that = (RestaurantItemEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(item, that.item)
                .append(restaurant, that.restaurant)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(item)
                .append(restaurant)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("item", item)
                .append("restaurant", restaurant)
                .toString();
    }
}
