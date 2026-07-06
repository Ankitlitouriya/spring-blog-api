package com.blogapi.service.impl;

import com.blogapi.dto.request.CategoryRequest;
import com.blogapi.dto.response.CategoryResponse;
import com.blogapi.entity.Category;
import com.blogapi.exception.ResourceAlreadyExistException;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        CategoryResponse categoryResponse = new CategoryResponse();
        if (categoryRepository.existsByName(request.getName())){
            throw new ResourceAlreadyExistException("category Already exist");
        }
        Category category = new Category();
        category.setName(request.getName());
       Category savedCategory = categoryRepository.save(category);
        categoryResponse.setName(savedCategory.getName());
        categoryResponse.setId(savedCategory.getId());
        return categoryResponse;
    }
    @Override
    public List<CategoryResponse> getAllCategories (){
         List<Category>categoryList = categoryRepository.findAll();
         return categoryList.stream().map(category->{
               CategoryResponse categoryResponse = new CategoryResponse();
               categoryResponse.setId(category.getId());
               categoryResponse.setName(category.getName());
               return categoryResponse;
         }).toList();
    }
    @Override
    public CategoryResponse findCategoryByID(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
    @Override
    public CategoryResponse updateCategories(long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found"));
        category.setName(request.getName());

        Category savedcategory = categoryRepository.save(category);
        CategoryResponse  categoryResponse = new CategoryResponse();
        categoryResponse.setId(savedcategory.getId());
        categoryResponse.setName(savedcategory.getName());
        return categoryResponse;
    }
    public  void deletCategories(long id){
           Category category = categoryRepository.findById(id)
                   .orElseThrow(()->new ResourceNotFoundException("Category not found"));
           categoryRepository.delete(category);

    }

}
