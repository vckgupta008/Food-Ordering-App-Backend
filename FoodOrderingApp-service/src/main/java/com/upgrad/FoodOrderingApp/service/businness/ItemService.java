package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemService {
    @Autowired
    private ItemDao itemDao;

    /**
     * Get  item details using item id
     *
     * @param itemId - String represents item id
     * @return - item details using item id
     */
    public ItemEntity getItemById(Integer itemId) {
        return itemDao.getItemById(itemId);
    }
}
