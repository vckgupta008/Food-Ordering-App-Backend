package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
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

}
