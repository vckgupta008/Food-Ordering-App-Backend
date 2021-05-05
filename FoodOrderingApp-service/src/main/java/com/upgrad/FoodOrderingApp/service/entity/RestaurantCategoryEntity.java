package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "restaurant_category")
@NamedQueries(
        {
                @NamedQuery(name = "getCategoriesByRestaurant",
                        query = "select rc from RestaurantCategoryEntity rc where rc.restaurant.uuid = :restaurantUuid order by rc.category.categoryName"),
                @NamedQuery(name = "getRestaurantsByCategory", query = "select rc from RestaurantCategoryEntity rc where rc.category.uuid =:categoryUuid")
        }
)
public class RestaurantCategoryEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "RESTAURANT_ID")
    private RestaurantEntity restaurant;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryEntity category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
