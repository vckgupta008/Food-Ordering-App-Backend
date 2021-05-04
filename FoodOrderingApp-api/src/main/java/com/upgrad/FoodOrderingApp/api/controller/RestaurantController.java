package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddressState;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CategoryService categoryService;

    /**
     * RestController method called when the request pattern is of type /restaurant"
     * and the incoming request is of 'GET' type
     * fetch all restaurants
     *
     * @return - ResponseEntity(RestaurantListResponse, HttpStatus.OK)
     */
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants() throws AddressNotFoundException {
        List<RestaurantEntity> allRestaurants = restaurantService.restaurantsByRating();

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();

        List<RestaurantList> restaurantLists = new ArrayList<RestaurantList>();

        //Looping through restaurant list
        for (RestaurantEntity restaurantEntity : allRestaurants) {
            RestaurantList restaurantList = new RestaurantList();

            //for each restaurant setting restaurant list
            restaurantList.setId(UUID.fromString(restaurantEntity.getUuid()));
            restaurantList.setRestaurantName(restaurantEntity.getRestaurantName());
            restaurantList.setPhotoURL(restaurantEntity.getPhotoUrl());
            restaurantList.setCustomerRating(new BigDecimal(restaurantEntity.getCustomerRating()));
            restaurantList.setAveragePrice(restaurantEntity.getAvgPrice());
            restaurantList.setNumberCustomersRated(restaurantEntity.getNumberCustomersRated());

            // setting address for every restaurant of type RestaurantDetailsResponseAddress
            RestaurantDetailsResponseAddress responseAddress = new RestaurantDetailsResponseAddress();
            AddressEntity addressEntity = restaurantEntity.getAddress();
            responseAddress.setId(UUID.fromString(addressEntity.getUuid()));
            responseAddress.setFlatBuildingName(addressEntity.getFlatBuildNum());
            responseAddress.setLocality(addressEntity.getLocality());
            responseAddress.setCity(addressEntity.getCity());
            responseAddress.setPincode(addressEntity.getPincode());

            StateEntity stateEntity = addressEntity.getState();

            //Setting state of that address of type RestaurantDetailsResponseAddressState
            RestaurantDetailsResponseAddressState responseAddressState = new RestaurantDetailsResponseAddressState();

            responseAddressState.setId(UUID.fromString(stateEntity.getUuid()));
            responseAddressState.setStateName(stateEntity.getStateName());

            //setting state of type RestaurantDetailsResponseAddressState in address of type RestaurantDetailsResponseAddress
            responseAddress.setState(responseAddressState);

            //set address of type RestaurantDetailsResponseAddress to restaurantList
            restaurantList.setAddress(responseAddress);

            List<String> categoryLists = new ArrayList();

            List<CategoryEntity> categoryEntities = categoryService.getCategoriesByRestaurant(restaurantEntity.getUuid());

            for (CategoryEntity categoryEntity :categoryEntities) {
                categoryLists.add(categoryEntity.getCategoryName());
            }

            restaurantList.setCategories(String.join(",", categoryLists));

            restaurantLists.add(restaurantList);

        }

        restaurantListResponse.setRestaurants(restaurantLists);

        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);

    }

}
