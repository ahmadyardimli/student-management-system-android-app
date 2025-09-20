package com.example.studentmanagementsystemandroidapp.apis.admin_apis.users.userandexamdetails;
import java.util.List;
import retrofit2.Call;

public interface UserAndExamDetailsCommonApi<Model, Req> {
    Call<List<Model>> getAllItems();
    Call<Model> createItem(Req request);
    Call<Model> updateItem(int itemId, Req request);
    Call<Void> deleteItem(int itemId);
    Call<Model> getItemById(int itemId);
}