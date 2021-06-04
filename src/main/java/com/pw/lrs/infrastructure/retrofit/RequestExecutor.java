package com.pw.lrs.infrastructure.retrofit;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class RequestExecutor {

    public <T> Response<T> execute(Call<T> call) {
        try {
            var response = call.execute();
            if (response.isSuccessful()) {
                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
            }
        } catch (IOException exception) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }
}
