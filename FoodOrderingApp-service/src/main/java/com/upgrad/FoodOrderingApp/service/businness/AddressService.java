package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.CommonValidation;
import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CommonValidation commonValidation;

    @Autowired
    private StateDao stateDao;

    /**
     * Method to get StateEntity from the database for the uuid provided
     *
     * @param stateUuid - String represents state Uuid
     * @return - StateEntity
     */
    public StateEntity getStateByUUID(final String stateUuid) throws AddressNotFoundException {
        StateEntity stateEntity = addressDao.getStateByUUID(stateUuid);

        // Throw exception if no state exists in the database with the provided state uuid
        if (stateEntity == null) {
            throw new AddressNotFoundException("ANF-002", "No state by this id");
        }
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
    public AddressEntity saveAddress(final AddressEntity addressEntity, final CustomerEntity customerEntity) throws SaveAddressException {

        // Throw exception if any of the required field is Empty
        if (commonValidation.isEmptyFieldValue((addressEntity.getFlatBuildNum()))
                || commonValidation.isEmptyFieldValue(addressEntity.getLocality())
                || commonValidation.isEmptyFieldValue(addressEntity.getCity())
                || commonValidation.isEmptyFieldValue(addressEntity.getPincode())
                || commonValidation.isEmptyFieldValue(addressEntity.getUuid())) {
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }

        // Throw exception if the pincode is invalid
        if (addressEntity.getPincode().length() != 6
                || !StringUtils.isNumeric(addressEntity.getPincode())) {
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }

        AddressEntity savedAddressEntity = addressDao.saveAddress(addressEntity);

        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setCustomer(customerEntity);
        customerAddressEntity.setAddress(savedAddressEntity);
        addressDao.saveCustomerAddr(customerAddressEntity);

        return savedAddressEntity;
    }

    /**
     * Method to retrieve all address for a customer
     *
     * @param customerEntity - CustomerEntity object for which the addresses need to be fetched
     * @return - List of AddressEntity
     */
    public List<AddressEntity> getAllAddress(final CustomerEntity customerEntity) {
        List<AddressEntity> addressEntities = new ArrayList<>();
        List<CustomerAddressEntity> customerAddressEntities = addressDao.getAllAddress(customerEntity);
        if (!customerAddressEntities.isEmpty()) {
            customerAddressEntities.forEach(
                    customerAddressEntity -> addressEntities.add(customerAddressEntity.getAddress()));
        }
        return addressEntities;
    }
    /**
     * Method to retrieve all states for a customer
     * Calls getAllStates of stateDao to get all States.
     * @return - List of all state name with id
     */
    public List<StateEntity> getAllStates(){
        List<StateEntity> stateEntities = stateDao.getAllStates();
        return stateEntities;
    }
}


