package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    /**
     * Method to get StateEntity from the database for the uuid provided
     *
     * @param stateUuid - String represents state Uuid
     * @return - StateEntity
     */
    public StateEntity getStateByUUID(final String stateUuid) {
        StateEntity stateEntity = addressDao.getStateByUUID(stateUuid);
        return stateEntity;
    }

    /**
     * Method to save AddressEntity into the database
     *
     * @param addressEntity  - AddressEntity to be persisted in thr database
     * @param customerEntity - CustomerEntity to be persisted in CustomerAddressEntity
     * @return - saved AddressEntity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(final AddressEntity addressEntity, final CustomerEntity customerEntity) {
        AddressEntity savedAddressEntity = addressDao.saveAddress(addressEntity);

        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setCustomer(customerEntity);
        customerAddressEntity.setAddress(savedAddressEntity);
        addressDao.saveCustomerAddr(customerAddressEntity);

        return savedAddressEntity;
    }
}
