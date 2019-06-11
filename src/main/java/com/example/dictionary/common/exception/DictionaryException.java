package com.example.dictionary.common.exception;

import com.example.dictionary.base.api.ApiState;
import lombok.Getter;

public class DictionaryException extends RuntimeException{
    @Getter
    private final ApiState state;

    private DictionaryException(ApiState state){
        this(state, state.getMessage());
    }

    private DictionaryException(ApiState state, String message){
        super(message);
        this.state = state;
    }

    private DictionaryException(int code, boolean status, String message){
        super(message);
        this.state = new ApiState() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public boolean isStatus() {
                return status;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    public static DictionaryException create (int code, boolean status, String message){
        return new DictionaryException(code, status, message);
    }

    public static DictionaryException create(ApiState state){
        return new DictionaryException(state);
    }

    public static DictionaryException create(ApiState state, String message){
        if (message == null)
            return new DictionaryException(state);
        return new DictionaryException(state, message);
    }
}
