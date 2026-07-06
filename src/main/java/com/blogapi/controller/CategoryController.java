package com.blogapi.controller;

import com.blogapi.dto.request.CategoryRequest;
import com.blogapi.dto.response.CategoryResponse;
import com.blogapi.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    //Create categories
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse>createdCategory(@RequestBody CategoryRequest request){
         CategoryResponse categoryResponse =  categoryService.createCategory(request);

         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(categoryResponse);
    }
    //find All
    @GetMapping
    public ResponseEntity<List<CategoryResponse>>getAllCategories(){
        List<CategoryResponse> categoryResponseList = categoryService.getAllCategories();

        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryResponseList);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse>findCategoryByID(@PathVariable long id){
        CategoryResponse categoryResponse = categoryService.findCategoryByID(id);

        return ResponseEntity.ok(categoryResponse);
    }
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse>updateCategories(@PathVariable long id,
                                                            @RequestBody CategoryRequest request )
    {
     CategoryResponse categoryResponse = categoryService.updateCategories(id,request);
     return ResponseEntity.status(HttpStatus.OK)
             .body(categoryResponse);
    }

    //delet categories

    @DeleteMapping("/{id}")

     public ResponseEntity<?> deletCategories(@PathVariable long id){
        categoryService.deletCategories(id);
        return ResponseEntity.noContent().build();
    }


}
