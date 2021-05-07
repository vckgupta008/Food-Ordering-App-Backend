package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private ItemService itemService;

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
            responseAddress.setFlatBuildingName(addressEntity.getFlatBuilNo());
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

    /**
     * RestController method called when the request pattern is of type /restaurant/name/{reastaurant_name}"
     * and the incoming request is of 'GET' type
     * fetch all restaurants matched with reastaurant_name
     *
     * @return - ResponseEntity(RestaurantListResponse, HttpStatus.OK)
     */
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{reastaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> restaurantsByName( @PathVariable String reastaurant_name) throws RestaurantNotFoundException {
        List<RestaurantEntity> allRestaurants = restaurantService.restaurantsByName(reastaurant_name);

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
            responseAddress.setFlatBuildingName(addressEntity.getFlatBuilNo());
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

    /**
     * RestController method called when the request pattern is of type /restaurant/category/{category_id}
     * and the incoming request is of 'GET' type
     * fetch all restaurants using category id
     * @param category_id      - String represents category id
     * @throws CategoryNotFoundException - if empty or invalid category is passed
     * @return - ResponseEntity(RestaurantListResponse, HttpStatus.OK)
     */

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> restaurantByUUID( @PathVariable String category_id) throws CategoryNotFoundException {
        List<RestaurantEntity> restaurantEntities = restaurantService.restaurantByCategory(category_id);

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();

        List<RestaurantList> restaurantLists = new ArrayList<RestaurantList>();
        for (RestaurantEntity restaurantEntity : restaurantEntities) {
            RestaurantList restaurantList = new RestaurantList();
//            RestaurantEntity restaurantEntity = restaurantCategoryEntity.getRestaurant();

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
            responseAddress.setFlatBuildingName(addressEntity.getFlatBuilNo());
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

    /**
     * RestController method called when the request pattern is of type /restaurant/{restaurant_id}
     * and the incoming request is of 'GET' type
     * fetch all restaurants using category id
     * @param restaurant_id      - String represents restaurant id
     * @throws RestaurantNotFoundException - if empty or invalid restaurant id is passed
     * @return - ResponseEntity(RestaurantDetailsResponse, HttpStatus.OK)
     */

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> restaurantByRestaurantId(@PathVariable("restaurant_id") final String restaurant_id) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurant_id);

        RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse();

        restaurantDetailsResponse.setId(UUID.fromString(restaurantEntity.getUuid()));
        restaurantDetailsResponse.setRestaurantName(restaurantEntity.getRestaurantName());
        restaurantDetailsResponse.setPhotoURL(restaurantEntity.getPhotoUrl());
        restaurantDetailsResponse.setCustomerRating(new BigDecimal(restaurantEntity.getCustomerRating()));
        restaurantDetailsResponse.setAveragePrice(restaurantEntity.getAvgPrice());
        restaurantDetailsResponse.setNumberCustomersRated(restaurantEntity.getNumberCustomersRated());

        // setting address for every restaurant of type RestaurantDetailsResponseAddress
        RestaurantDetailsResponseAddress responseAddress = new RestaurantDetailsResponseAddress();
        AddressEntity addressEntity = restaurantEntity.getAddress();
        responseAddress.setId(UUID.fromString(addressEntity.getUuid()));
        responseAddress.setFlatBuildingName(addressEntity.getFlatBuilNo());
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
        restaurantDetailsResponse.setAddress(responseAddress);


        List<CategoryList> categoryLists = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryService.getCategoriesByRestaurant(restaurant_id);

        for(CategoryEntity categoryEntity: categoryEntities){
            CategoryList categoryList = new CategoryList();
            categoryList.setId(UUID.fromString(categoryEntity.getUuid()));
            categoryList.setCategoryName(categoryEntity.getCategoryName());
            List<ItemEntity> itemEntities = itemService.getItemsByCategoryAndRestaurant(restaurant_id,categoryEntity.getUuid());

            List<ItemList> itemLists = new ArrayList<>();
            for(ItemEntity itemEntity: itemEntities){
                itemLists.add(setItemList(itemEntity));
            }
            categoryList.setItemList(itemLists);
            categoryLists.add(categoryList);
        }

        restaurantDetailsResponse.setCategories(categoryLists);

        return new ResponseEntity<RestaurantDetailsResponse>(restaurantDetailsResponse, HttpStatus.OK);

    }

    private ItemList setItemList(ItemEntity itemEntity) {
        ItemList itemList = new ItemList();
        itemList.setId(UUID.fromString(itemEntity.getUuid()));
        itemList.setItemName(itemEntity.getItemName());
        itemList.setPrice(itemEntity.getPrice());
        String itemType = itemEntity.getType().equals(0)? "VEG":"NON_VEG";
        itemList.setItemType(ItemList.ItemTypeEnum.fromValue(itemType));
        return itemList;
    }

}

