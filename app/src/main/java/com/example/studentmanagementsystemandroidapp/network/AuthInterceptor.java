package com.example.studentmanagementsystemandroidapp.network;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.example.studentmanagementsystemandroidapp.auth.TokenStore;

public class AuthInterceptor implements Interceptor {
    private final TokenStore store;
    public AuthInterceptor(TokenStore store) { this.store = store; }

    @Override public Response intercept(Chain chain) throws IOException {
        Request req = chain.request();
        String at = store.getAccessToken(); // I store "Bearer <jwt>" from backend
        if (at != null && !at.isEmpty()) {
            req = req.newBuilder().header("Authorization", at).build();
        }
        return chain.proceed(req);
    }
}