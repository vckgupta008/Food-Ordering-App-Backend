package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.CommonValidation;
import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
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

    @Autowired
    private CommonValidation commonValidation;

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
     * @param restaurantName - String representing restaurant name
     * @return - list of Restaurant Entities using restaurant name
     * @throws RestaurantNotFoundException - if the restaurant name is empty
     */
    public List<RestaurantEntity> restaurantsByName(final String restaurantName) throws RestaurantNotFoundException {
        // Throw exception if the restaurant name is empty
        if (commonValidation.isEmptyFieldValue(restaurantName)) {
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        List<RestaurantEntity> restaurantEntities = restaurantDao.restaurantsByName(restaurantName);

        return restaurantEntities;
    }

    /**
     * Method to get all restaurants using category uuid
     *
     * @param categoryUuid - String represents category UUID
     * @return - list of Restaurant Entities using  category uuid
     * @throws CategoryNotFoundException - if category uuid is empty, or no category exists for the give category uuid
     */
    public List<RestaurantEntity> restaurantByCategory(final String categoryUuid) throws CategoryNotFoundException {
        // Throw exception if the category uuid is empty
        if (categoryUuid.isEmpty()) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryUuid);
        // Throw exception if no category exists for the give category uuid
        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        List<RestaurantEntity> restaurantEntities = new ArrayList<>();

        List<RestaurantCategoryEntity> restaurantCategoryEntities = restaurantDao
                .restaurantsByCategoryId(categoryEntity.getUuid());
        restaurantCategoryEntities.forEach(restaurantCategoryEntity -> {
            restaurantEntities.add(restaurantCategoryEntity.getRestaurant());
        });
        return restaurantEntities;
    }

    /**
     * Method to retrieve RestaurantEntity for the given restaurant UUID
     * @param restaurantUuid - String represents restaurant UUID
     * @return - RestaurantEntity object
     * @throws RestaurantNotFoundException - if no restaurant in found in the database for the given restaurant uuid
     */
    public RestaurantEntity restaurantByUUID(final String restaurantUuid) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantDao.restaurantByUUID(restaurantUuid);
        // Throw exception if no restaurant in found in the database for the given restaurant uuid
        if (restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return restaurantEntity;
    }

}
