package mx.com.axity.services.service;

import mx.com.axity.commons.to.CategoryTO;
import mx.com.axity.commons.to.SubCategoryTO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DiscountServiceTest extends BaseTest {

    @Test
    public void getCategoryTest() {
        var category = this.discountServiceTest.getCategory();
        Assertions.assertNotNull(category);
    }

    @Test
    public void getSubcategoryTest() {
        var subcategory = this.discountServiceTest.getSubcategory(1L);
        Assertions.assertNotNull(subcategory);
    }

    @Test
    public void getPagedDiscountTest() {
        var pagedDiscount = this.discountServiceTest.getPagedDiscount(0,"","","","");
        Assertions.assertNotNull(pagedDiscount);
    }

    @Test
    public void saveCategoryTest() {
        CategoryTO category = new CategoryTO();
        category.setCategory("test");
        category.setLastUserModifier("test");
        category.setLastModification(LocalDate.now());
        category.setCreationUser("test");
        category.setCreationDate(LocalDate.now());
        category.setActive(Boolean.TRUE);
         this.discountServiceTest.saveCategory(category);
    }

    @Test
    public void saveSubcategoryTest() {
        SubCategoryTO subCategoryTO = new SubCategoryTO();
        CategoryTO category = new CategoryTO();
        category.setIdCategory((long)1);
        category.setCategory("test");
        category.setLastUserModifier("test");
        category.setLastModification(LocalDate.now());
        category.setCreationUser("test");
        category.setCreationDate(LocalDate.now());
        category.setActive(Boolean.TRUE);
        subCategoryTO.setCategory(category);
        subCategoryTO.setSubcategory("test");
        subCategoryTO.setLastUserModifier("test");
        subCategoryTO.setLastModification(LocalDate.now());
        subCategoryTO.setCreationUser("test");
        subCategoryTO.setCreationDate(LocalDate.now());
        subCategoryTO.setActive(Boolean.TRUE);
        this.discountServiceTest.saveSubcategory(subCategoryTO);
    }

}
