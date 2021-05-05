package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
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

    /**
     * Get  category details using category uuid
     *
     * @param categoryUuid - String represents category uuid
     * @return - category details using category uuid
     */
    public CategoryEntity getCategoryById(String categoryUuid) {
        try {
            return entityManager.createNamedQuery("getCategoryUsingUuid", CategoryEntity.class)
                    .setParameter("categoryUuid", categoryUuid)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    /**
     * Method to get all RestaurantCategoryEntity using restaurant uuid
     *
     * @return -  get all RestaurantCategoryEntity  using restaurant uuid
     */
    public List<RestaurantCategoryEntity> getCategoriesByRestaurant(String restaurantUuid) {
        try {
            return entityManager.createNamedQuery("getCategoriesByRestaurant", RestaurantCategoryEntity.class)
                    .setParameter("restaurantUuid",restaurantUuid)
                    .getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

}
