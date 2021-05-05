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
    /**
     * Method to get AddressEntity for the given address UUID
     *
     * @param addressUuid - Address UUID
     * @return AddressEntity object
     */
    public AddressEntity getAddressByUUID(final String addressUuid) {
        try {
            return entityManager.createNamedQuery("addressByUuid", AddressEntity.class)
                    .setParameter("addressUuid", addressUuid)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Method to get CustomerAddressEntity for the given address and customer object
     *
     * @param address  - AddressEntity object
     * @param customer - CustomerEntity object
     * @return CustomerAddressEntity object if found in the database, else return null
     */
    public CustomerAddressEntity getCustomerAddress(AddressEntity address, CustomerEntity customer) {
        try {
            return entityManager.createNamedQuery("customerAddressByCustIDAddrID", CustomerAddressEntity.class)
                    .setParameter("customer", customer)
                    .setParameter("address", address)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Method to delete AddressEntity object from the database
     *
     * @param addressEntity - AddressEntity to be deleted
     */
    public void deleteAddress(AddressEntity addressEntity) {
        entityManager.remove(addressEntity);
    }
}
