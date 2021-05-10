package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * The ItemEntity class is mapped to table 'item' in database
 * All the columns are mapped to its respective attributes of the class
 */

@Entity
@Table(name = "item")
@NamedQueries(
        {
                @NamedQuery(name = "itemById",
                        query = "select i from ItemEntity i where i.uuid= :itemUuid"),
                @NamedQuery(name = "itemsByCategoryByRestaurant",
                        query = "select i from ItemEntity i  where i.id in (select ri.item.id from RestaurantItemEntity ri "
                                + "inner join CategoryItemEntity ci on ri.item.id = ci.item.id "
                                + "where ri.restaurant.uuid = :restaurantUuid "
                                + "and ci.category.uuid = :categoryUuid)"
                                + "order by i.itemName asc")
        }
)
@NamedNativeQueries({
        // Using native query as named queries do not support some SQL properties
        @NamedNativeQuery(name = "topFivePopularItemsByRestaurant",
        query = "select a.* from item a " +
                "inner join restaurant_item d " +
                "on a.id = d.item_id " +
                "left join " +
                "(select oi.item_id, sum(case when oi.order_id is null then 0 else 1 end) as cnt from order_item oi " +
                "INNER join orders o on oi.order_id = o.id " +
                "and o.restaurant_id = ? " +
                "group by oi.item_id " +
                "order by cnt desc LIMIT 5) b " +
                "on a.id = b.item_id " +
                "order by coalesce(b.cnt,0) desc " +
                "limit 5",
                resultClass = ItemEntity.class)
})
public class ItemEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "ITEM_NAME")
    @Size(max = 30)
    @NotNull
    private String itemName;

    @Column(name = "PRICE")
    @NotNull
    private Integer price;

    @Column(name = "TYPE")
    @Size(max = 10)
    @NotNull
    private String type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private List<CategoryEntity> categories;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<RestaurantEntity> restaurants;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<OrderEntity> orders;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public List<RestaurantEntity> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantEntity> restaurants) {
        this.restaurants = restaurants;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemEntity that = (ItemEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(uuid, that.uuid)
                .append(itemName, that.itemName)
                .append(price, that.price)
                .append(type, that.type)
                .append(categories, that.categories)
                .append(restaurants, that.restaurants)
                .append(orders, that.orders)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(uuid)
                .append(itemName)
                .append(price)
                .append(type)
                .append(categories)
                .append(restaurants)
                .append(orders)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("uuid", uuid)
                .append("itemName", itemName)
                .append("price", price)
                .append("type", type)
                .append("categories", categories)
                .append("restaurants", restaurants)
                .append("orders", orders)
                .toString();
    }
}
