package com.blogapi.service;

import com.blogapi.dto.request.CategoryRequest;
import com.blogapi.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
//    CategoryResponse getAllCategories();
    List<CategoryResponse> getAllCategories ();

    CategoryResponse findCategoryByID(long id);

    CategoryResponse updateCategories(long id,CategoryRequest request);

    void  deletCategories(long id);

}
