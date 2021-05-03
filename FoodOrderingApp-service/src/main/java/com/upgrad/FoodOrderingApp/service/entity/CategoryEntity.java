package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * The CategoryEntity class is mapped to table 'category' in database
 * All the columns are mapped to its respective attributes of the class
 */

@Entity
@Table(name = "category")
@NamedQueries(
        {
                @NamedQuery(name = "getAllCategoriesOrderedByName",
                        query = "select c from CategoryEntity c order by c.category_name"),
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
    private String category_name;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
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


}
