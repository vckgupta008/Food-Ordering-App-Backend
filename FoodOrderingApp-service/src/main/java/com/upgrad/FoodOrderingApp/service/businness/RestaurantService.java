package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CategoryDao categoryDao;

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
        if (restaurantName.isEmpty()) {
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        List<RestaurantEntity> restaurantEntities = restaurantDao.restaurantsByName(restaurantName);

        return restaurantEntities;
    }

    /**
     * Method to get all restaurants using category uuid
     *
     * @return - list of Restaurant Entities using  category uuid
     */
    public List<RestaurantEntity> restaurantByCategory(String categoryUuid) throws CategoryNotFoundException {
        if (categoryUuid.isEmpty()) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }
        CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryUuid);
        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        List<RestaurantEntity> restaurantEntities = new ArrayList<>();

        List<RestaurantCategoryEntity> restaurantCategoryEntities =restaurantDao.restaurantsByCategoryId(categoryEntity.getUuid());
        restaurantCategoryEntities.forEach(restaurantCategoryEntity -> {
            restaurantEntities.add(restaurantCategoryEntity.getRestaurant());
        });
        return restaurantEntities;
    }

    //restaurantByCategory
}
