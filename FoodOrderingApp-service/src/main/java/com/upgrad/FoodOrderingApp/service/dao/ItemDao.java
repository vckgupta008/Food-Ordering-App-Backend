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
     * Get  item details using item uuid
     *
     * @param itemUuid - String represents item uuid
     * @return - item details using item uuid
     */
    public ItemEntity getItemById(final String itemUuid) {
        try {
            return entityManager.createNamedQuery("itemById", ItemEntity.class)
                    .setParameter("itemUuid", itemUuid)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
