package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class ItemDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get  item details using item id
     *
     * @param itemId - String represents item id
     * @return - item details using item id
     */
    public ItemEntity getItemById(Integer itemId) {
        try {
            return entityManager.createNamedQuery("getItemById", ItemEntity.class)
                    .setParameter("itemId", itemId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
