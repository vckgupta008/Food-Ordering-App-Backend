package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantDao restaurantDao;

    /**
     * Method to get all restaurants in order of their ratings
     *
     * @return - list of Restaurant Entities in order of their ratings
     */
    public List<RestaurantEntity> restaurantsByRating() {
        return restaurantDao.getAllRestaurants();
    }

    /**
     * Method to get all restaurants using restaurant name
     *
     * @return - list of Restaurant Entities using restaurant name
     */
    public List<RestaurantEntity> restaurantsByName(String restaurantName) throws RestaurantNotFoundException {
        if(restaurantName.isEmpty()){
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        List<RestaurantEntity> restaurantEntities = restaurantDao.restaurantsByName(restaurantName);

        return restaurantEntities;
    }
}
