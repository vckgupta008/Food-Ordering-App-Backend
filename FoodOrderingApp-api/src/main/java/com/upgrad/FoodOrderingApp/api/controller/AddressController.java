package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.common.CommonValidation;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CommonValidation commonValidation;

    /**
     * RestController method called when the request pattern is of type '/address'
     * and the incoming request is of 'POST' type
     * Persists SaveAddressRequest details in the database
     *
     * @param authorization      - String represents authorization token
     * @param saveAddressRequest - SaveAddressRequest details to be persisted in the database
     * @return - ResponseEntity (SaveAddressResponse along with HTTP status code)
     * @throws AuthorizationFailedException - if the access token is not valid/ customer has already logged out/
     *                                      *                                      the session has already expired
     * @throws SaveAddressException         - if any required field is empty, or pincode is invalid
     * @throws AddressNotFoundException     - if no state exists with the provided state uuid
     */
    @RequestMapping(method = RequestMethod.POST, path = "/address",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(@RequestHeader("authorization") final String authorization,
                                                           @RequestBody(required = false) final SaveAddressRequest saveAddressRequest)
            throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException {

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        StateEntity state = addressService.getStateByUUID(saveAddressRequest.getStateUuid());

        // Set fields into AddressEntity
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setUuid(UUID.randomUUID().toString());
        addressEntity.setFlatBuilNo(saveAddressRequest.getFlatBuildingName());
        addressEntity.setLocality(saveAddressRequest.getLocality());
        addressEntity.setCity(saveAddressRequest.getCity());
        addressEntity.setPincode(saveAddressRequest.getPincode());
        addressEntity.setActive(1);
        addressEntity.setState(state);

        AddressEntity savedAddressEntity = addressService.saveAddress(addressEntity, customerEntity);

        SaveAddressResponse saveAddressResponse = new SaveAddressResponse()
                .id(savedAddressEntity.getUuid())
                .status("ADDRESS SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);
    }

    /**
     * RestController method called when the request pattern is of type '/address/customer'
     * and the incoming request is of 'GET' type
     * Get all the saved address from the database for a customer
     *
     * @param authorization - String represents authorization token
     * @return - ResponseEntity (AddressListResponse containing customer's address, along with HTTP status code)
     * @throws AuthorizationFailedException - if the access token is not valid/ customer has already logged out/
     *                                      the session has already expired
     */
    @RequestMapping(method = RequestMethod.GET, path = "/address/customer",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAllAddress(@RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        List<AddressEntity> allAddress = addressService.getAllAddress(customerEntity);
        List<AddressList> addressLists = new ArrayList<>();
        if (!allAddress.isEmpty()) {
            allAddress.forEach(
                    address -> addressLists.add(setAddressList(address)));
        }
        ;

        AddressListResponse addressListResponse = new AddressListResponse()
                .addresses(addressLists);

        return new ResponseEntity<AddressListResponse>(addressListResponse, HttpStatus.OK);

    }

    /**
     * Method to set all AddressEntity field into AddressList
     *
     * @param address - AddressEntity object
     * @return - AddressList
     */
    private AddressList setAddressList(Object address) {
        AddressList addressList = new AddressList();
        AddressEntity addressEntity = (AddressEntity) address;
        addressList.setId(UUID.fromString(addressEntity.getUuid()));
        addressList.setFlatBuildingName(addressEntity.getFlatBuilNo());
        addressList.setLocality(addressEntity.getLocality());
        addressList.setCity(addressEntity.getCity());
        addressList.setPincode(addressEntity.getPincode());
        AddressListState addressListState = new AddressListState();
        addressListState.id(UUID.fromString(addressEntity.getState().getUuid()))
                .stateName(addressEntity.getState().getStateName());
        addressList.setState(addressListState);
        return addressList;
    }

    /**
     * RestController method called when the request pattern is of type '/address/{address_id}'
     * and the incoming request is of 'DELETE' type
     * Delete the address from the database for a customer
     *
     * @param authorization - String represents authorization token
     * @param addressId     - Address UUID to be deleted from the database
     * @return - ResponseEntity (DeleteAddressResponse along with HTTP status code)
     * @throws AuthorizationFailedException - if the access token is not valid/ customer has already logged out/
     *                                      the session has already expired
     * @throws AddressNotFoundException     - if the address UUID is empty, or incorrect
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/address/{address_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> deleteSavedAddress(@RequestHeader("authorization") final String authorization,
                                                                    @PathVariable("address_id") final String addressId)
            throws AuthorizationFailedException, AddressNotFoundException {

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        // Throw exception if the address UUID is empty
        if (commonValidation.isEmptyFieldValue(addressId)) {
            throw new AddressNotFoundException("ANF-005", "Address id can not be empty");
        }

        AddressEntity addressEntity = addressService.getAddressByUUID(addressId, customerEntity);
        AddressEntity deletedAddressEntity = addressService.deleteAddress(addressEntity);

        DeleteAddressResponse deleteAddressResponse = new DeleteAddressResponse()
                .id(UUID.fromString(deletedAddressEntity.getUuid()))
                .status("ADDRESS DELETED SUCCESSFULLY");

        return new ResponseEntity<DeleteAddressResponse>(deleteAddressResponse, HttpStatus.OK);

    }
    /**
     * Method to get getAllStates method in addressService and returns list of stateEntity.
     *
     * Any user can access this stateList
     *
     * @return - ResponseEntity<StatesListResponse>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/states",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<StatesListResponse> getAllStates(){


        List<StateEntity> stateEntities = addressService.getAllStates();

        if(!stateEntities.isEmpty()) {//Checking if StateEntities is empty.
            //Creates List of StateList using Model StateList.
            List<StatesList> statesLists = new ArrayList<> ();
            //looping in to get details of all the the stateEntity & then create a stateList and add UUID of state and stateName and add the newly created StateList to the list of StateList.
            stateEntities.forEach(stateEntity -> {
                StatesList statesList = new StatesList()
                        .id(UUID.fromString(stateEntity.getUuid ()))
                        .stateName(stateEntity.getStateName());
                statesLists.add(statesList);
            });

            //Creating StatesListResponse and adding list of stateLists
            StatesListResponse statesListResponse = new StatesListResponse().states(statesLists);
            return new ResponseEntity<StatesListResponse>(statesListResponse, HttpStatus.OK);
        }else
            //Return empty set if stateEntities is empty.
            return new ResponseEntity<StatesListResponse>(new StatesListResponse(),HttpStatus.OK);
    }
}