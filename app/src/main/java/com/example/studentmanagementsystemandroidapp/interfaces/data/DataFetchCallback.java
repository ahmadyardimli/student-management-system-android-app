package com.example.studentmanagementsystemandroidapp.interfaces.data;

import java.util.List;

import retrofit2.Response;

public interface DataFetchCallback<T> {
    void onDataFetched(List<T> data);
    void onSingleItemFetched(T item);
    void onDataFetchFailed(Throwable throwable);
    void onUnsuccessfulResponseFetched(Response response);
}

