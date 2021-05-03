package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
     */
    @RequestMapping(method = RequestMethod.GET, path = "/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategoriesOrderedByName() {
        List<CategoryEntity> allCategories = categoryService.getAllCategoriesOrderedByName();

        List<CategoryListResponse> categoryLists = new ArrayList<>();

        if (!allCategories.isEmpty()) {
            allCategories.forEach(
                    category -> categoryLists.add(setCategoryList(category))
            );
        }
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse().categories(categoryLists);

        return new ResponseEntity<CategoriesListResponse>(categoriesListResponse, HttpStatus.OK);

    }

    /**
     * RestController method called when the request pattern is of type "/order/coupon/{coupon_name}"
     * and the incoming request is of 'GET' type
     * Retrieve coupon details using coupon name
     *
     * @param category_id - This represents coupon name
     * @return - ResponseEntity(CouponDetailsResponse, HttpStatus.OK)
     * @throws CategoryNotFoundException - if incorrect/ invalid category id is sent,
     *                                   *                                      or the category id doesn't exist
     */
    @RequestMapping(method = RequestMethod.GET, path = "/category/{category_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryById(
            @PathVariable String category_id) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryService.getCategoryById(category_id);
        System.out.print(categoryEntity.getItems().size());
        List<ItemEntity> itemEntities = categoryEntity.getItems();
        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse();
        categoryDetailsResponse.setId(UUID.fromString(categoryEntity.getUuid()));
        categoryDetailsResponse.setCategoryName(categoryEntity.getCategoryName());
        List<ItemList> itemLists = new ArrayList<>();
        if (!itemEntities.isEmpty()) {
            itemEntities.forEach(itemEntity -> {
                itemLists.add(setItemList(itemEntity));
            });
            categoryDetailsResponse.setItemList(itemLists);
        }
        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse, HttpStatus.OK);
    }

    /**
     * Method to set and return ItemList
     *
     * @param itemEntity - itemEntity object
     * @return - ItemList
     */
    private ItemList setItemList(ItemEntity itemEntity) {
        ItemList itemList = new ItemList();
        itemList.setId(UUID.fromString(itemEntity.getUuid()));
        itemList.setItemName(itemEntity.getItemName());
        itemList.setPrice(itemEntity.getPrice());
        String itemType = itemEntity.getType().equals(0)? "VEG":"NON_VEG";
        itemList.setItemType(ItemList.ItemTypeEnum.fromValue(itemType));
        return itemList;
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
        categoryListResponse.setCategoryName(categoryEntity.getCategoryName());
        categoryListResponse.setId(UUID.fromString(categoryEntity.getUuid()));
        return categoryListResponse;
    }
}
