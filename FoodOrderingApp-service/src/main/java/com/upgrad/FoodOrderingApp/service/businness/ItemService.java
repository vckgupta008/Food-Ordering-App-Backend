package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemDao itemDao;

    @Autowired
    private CategoryDao categoryDao;

    /**
     * Method tp retrieve item details using item uuid
     *
     * @param itemUuid - String represents item uuid
     * @return - item details using item uuid
     * @throws ItemNotFoundException - if no ItemEntity is found in the databse for the given item uuid
     */
    public ItemEntity getItemById(final String itemUuid) throws ItemNotFoundException {
        ItemEntity itemEntity = itemDao.getItemById(itemUuid);
        if (itemEntity == null) {
            throw new ItemNotFoundException("INF-003", "No item by this id exist");
        }
        return itemEntity;
    }

    /**
     * Method tp retrieve list of item details restaurantUuid and categoryUuid
     *
     * @param restaurantUuid - String represents restaurant uuid
     * @param categoryUuid - String represents category uuid
     * @return - List of item details using restaurantUuid and categoryUuid
     */

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantUuid,String categoryUuid){
        CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryUuid);
        List<ItemEntity> itemEntities = categoryEntity.getItems();
        return itemEntities;
    }
}
