package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * RestController method called when the request pattern is of type "/category"
     * and the incoming request is of 'GET' type
     * Retrieve category list order by name
     *
     * @return - ResponseEntity(CategoriesListResponse, HttpStatus.OK)
     *
     */
    @RequestMapping(method = RequestMethod.GET, path = "/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategoriesOrderedByName(){
        List<CategoryEntity> allCategories=categoryService.getAllCategoriesOrderedByName();

        List<CategoryListResponse> categoryLists = new ArrayList<>();

        if(!allCategories.isEmpty()){
            allCategories.forEach(
                    category -> categoryLists.add(setCategoryList(category))
            );
        }
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse().categories(categoryLists);

        return new ResponseEntity<CategoriesListResponse>(categoriesListResponse, HttpStatus.OK);

    }

    /**
     * Method to set all  field into CategoryListResponse
     *
     * @param category - CategoryEntity object
     * @return - CategoryListResponse
     */
    private CategoryListResponse setCategoryList(Object category) {
        CategoryEntity categoryEntity = (CategoryEntity) category;
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        categoryListResponse.setCategoryName(categoryEntity.getCategory_name());
        categoryListResponse.setId(UUID.fromString(categoryEntity.getUuid()));
        return categoryListResponse;
    }
}
