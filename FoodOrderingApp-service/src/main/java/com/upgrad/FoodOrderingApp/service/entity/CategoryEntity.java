package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * The CategoryEntity class is mapped to table 'category' in database
 * All the columns are mapped to its respective attributes of the class
 */

@Entity
@Table(name = "category")
@NamedQueries(
        {
                @NamedQuery(name = "getAllCategoriesOrderedByName",
                        query = "select c from CategoryEntity c order by c.categoryName"),
                @NamedQuery(name = "getCategoryUsingUuid",
                        query = "select c from CategoryEntity c where c.uuid = :categoryUuid"),
                @NamedQuery(name = "getCategoryUsingRestaurantUuid",
                        query = "select c from CategoryEntity c where c.uuid = :categoryUuid")
        }
)
public class CategoryEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private List<ItemEntity> items;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
//    private List<RestaurantEntity> restaurants;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

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

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }


}
