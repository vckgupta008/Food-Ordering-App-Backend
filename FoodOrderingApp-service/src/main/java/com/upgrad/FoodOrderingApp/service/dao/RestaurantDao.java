package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method to get all restaurants in order of their ratings
     *
     * @return - list of Restaurant Entities in order of their ratings
     */
    public List<RestaurantEntity> getAllRestaurants() {
        try {
            return entityManager.createNamedQuery("allRestaurants", RestaurantEntity.class).getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

}
