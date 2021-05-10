package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class ItemController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemService itemService;

    /**
     * RestController method called when the request pattern is of type '/item/restaurant/{restaurant_id}'
     * and the incoming request is of 'GET' type
     * Retrieve the top five items of that restaurant based on the number of times that item was ordered
     *
     * @param restaurantId - Restaurant UUID
     * @return - ResponseEntity(ItemListResponse along with HTTP status code)
     * @throws RestaurantNotFoundException - if no restaurant exists in the databse for the provided restaurant UUID
     */
    @RequestMapping(method = RequestMethod.GET, path = "/item/restaurant/{restaurant_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ItemListResponse> getTopFiveItems(@PathVariable("restaurant_id") final String restaurantId)
            throws RestaurantNotFoundException {
        final RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantId);
        final List<ItemEntity> itemEntities = itemService.getItemsByPopularity(restaurantEntity);

        final ItemListResponse itemListResponse = new ItemListResponse();
        if (!itemEntities.isEmpty()) {
            itemEntities.forEach(itemEntity -> itemListResponse.add(createItemList(itemEntity)));
        }

        return new ResponseEntity<>(itemListResponse, HttpStatus.OK);
    }

    /**
     * Method to set ItemEntity into ItemList
     *
     * @param itemEntity - ItemEntity object
     * @return - ItemList object
     */
    private ItemList createItemList(final ItemEntity itemEntity) {
        final ItemList itemList = new ItemList();
        itemList.id(UUID.fromString(itemEntity.getUuid()));
        itemList.itemName(itemEntity.getItemName());
        itemList.price(itemEntity.getPrice());
        final String itemType = itemEntity.getType().equals("0") ? "VEG" : "NON_VEG";
        itemList.itemType(ItemList.ItemTypeEnum.fromValue(itemType));
        return itemList;
    }
}
