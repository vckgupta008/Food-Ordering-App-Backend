package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

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

    /**
     * Method to retrieve all addresses from the database for a customer
     *
     * @param customerEntity - CustomerEntity object
     * @return - List of CustomerAddressEntity
     */
    public List<CustomerAddressEntity> getAllAddress(final CustomerEntity customerEntity) {
        List<CustomerAddressEntity> addresses = entityManager
                .createNamedQuery("customerAddressByCustomer", CustomerAddressEntity.class)
                .setParameter("customer", customerEntity)
                .getResultList();
        if (addresses == null) {
            return Collections.emptyList();
        }
        return addresses;
    }
}
