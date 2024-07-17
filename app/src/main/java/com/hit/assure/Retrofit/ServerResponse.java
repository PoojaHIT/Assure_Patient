package com.hit.assure.Retrofit;

public interface ServerResponse<T> {
    void success(T t, int code);
    void error(T t, int code);
}
