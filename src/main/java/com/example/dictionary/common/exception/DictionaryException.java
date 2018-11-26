package com.example.dictionary.common.exception;

import com.example.dictionary.common.model.ApiResult;
import lombok.Data;

@Data
public class DictionaryException extends RuntimeException{
    private static final long serialVersionUID = 4564124491192825231L;

    private ApiResult.STATE state;

    public DictionaryException(ApiResult.STATE state){
        super();
        this.setState(state);
    }

    public DictionaryException(ApiResult.STATE state, String message){
        super(message);
        this.setState(state);
    }
}
