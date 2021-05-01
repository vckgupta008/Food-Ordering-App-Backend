package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get  all categories
     *
     * @return - list of categories entities else null
     */
    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        try {
            List<CategoryEntity> allCategories =entityManager.createNamedQuery("getAllCategoriesOrderedByName", CategoryEntity.class)
                    .getResultList();
            return allCategories;
        } catch (NoResultException nre) {
            return null;
        }
    }
}
