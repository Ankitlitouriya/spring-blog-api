package com.blogapi.service.impl;

import com.blogapi.dto.request.CategoryRequest;
import com.blogapi.dto.response.CategoryResponse;
import com.blogapi.entity.Category;
import com.blogapi.exception.ResourceAlreadyExistException;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.mapper.CategoryMapper;
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

    private final CategoryMapper categoryMapper;
   // Create category
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())){
            throw new ResourceAlreadyExistException("category Already exist");
        }
        Category category = new Category();
        category.setName(request.getName());
       Category savedCategory = categoryRepository.save(category);
       return categoryMapper.toResponse(savedCategory);
    }

    // Get all category
    @Override
    public List<CategoryResponse> getAllCategories (){
         List<Category>categoryList = categoryRepository.findAll();
         return categoryList.stream().map(categoryMapper::toResponse
         ).toList();
    }
    // find category by Id
    @Override
    public CategoryResponse findCategoryByID(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));
         return categoryMapper.toResponse(category);
    }
    //Update catgory
    @Override
    public CategoryResponse updateCategories(long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found"));

        category.setName(request.getName());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }
    //Delete category
    public  void deletCategories(long id){
           Category category = categoryRepository.findById(id)
                   .orElseThrow(()->new ResourceNotFoundException("Category not found"));
           categoryRepository.delete(category);

    }

}
