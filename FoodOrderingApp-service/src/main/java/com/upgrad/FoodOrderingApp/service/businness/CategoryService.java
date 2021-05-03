package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.CommonValidation;
import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;


    @Autowired
    private CommonValidation commonValidation;

    /**
     * Get  all categories
     *
     * @return - list of CategoryEntity
     */
    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        List<CategoryEntity> allCategory = categoryDao.getAllCategoriesOrderedByName();
        return allCategory;
    }

    /**
     * Get  category details using category uuid
     *
     * @param categoryUuid - String represents category uuid
     * @return - category details using category uuid
     */
    public CategoryEntity getCategoryById(String categoryUuid) throws CategoryNotFoundException {
        if (commonValidation.isEmptyFieldValue(categoryUuid)) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }
        CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryUuid);

        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }
        return categoryEntity;

    }
}
