package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method to get StateEntity from the database for the provided state uuid
     *
     * @param stateUuid - STring represents state uuid
     * @return - StateEntity if exists in the database, else return null
     */
    public StateEntity getStateByUUID(String stateUuid) {
        try {
            return entityManager.createNamedQuery("stateByUUID", StateEntity.class)
                    .setParameter("stateUuid", stateUuid)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Method to save AddressEntity in the database
     *
     * @param addressEntity - AddressEntity to be persisted in the database
     * @return - saved AddressEntity
     */
    public AddressEntity saveAddress(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    /**
     * Method to save CustomerAddressEntity in the database
     *
     * @param customerAddressEntity - CustomerAddressEntity to be persisted in the database
     */
    public void saveCustomerAddr(CustomerAddressEntity customerAddressEntity) {
        entityManager.persist(customerAddressEntity);
    }
}
