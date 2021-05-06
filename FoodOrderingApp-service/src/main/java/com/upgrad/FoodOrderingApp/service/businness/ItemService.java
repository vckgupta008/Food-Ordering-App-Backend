package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemDao itemDao;

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
}
