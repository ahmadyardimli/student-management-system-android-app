package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;

import com.example.studentmanagementsystemandroidapp.models.userandexamdetails.Category;
import com.example.studentmanagementsystemandroidapp.requests.userandexamdetails.CategoryRequest;

import java.util.List;

import retrofit2.Call;

public class CategoryApiImpl implements UserAndExamDetailsCommonApi<Category, CategoryRequest> {
    private final CategoryApi categoryApi;

    public CategoryApiImpl(CategoryApi categoryApi) {
        this.categoryApi = categoryApi;
    }

    @Override
    public Call<List<Category>> getAllItems() {
        return categoryApi.getAllCategories();
    }

    @Override
    public Call<Category> createItem(CategoryRequest request) {
        return categoryApi.createCategory(request);
    }

    @Override
    public Call<Category> updateItem(int itemId, CategoryRequest request) {
        return categoryApi.updateCategory(itemId, request);
    }

    @Override
    public Call<Void> deleteItem(int itemId) {
        return categoryApi.deleteCategory(itemId);
    }

    @Override
    public Call<Category> getItemById(int itemId) {
        return null;
    }
}
