package com.example.graphql.exception;

import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.ErrorType;

public class QAndAAuthenticationErrorDetail implements ErrorDetail {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.UNAUTHENTICATED;
    }

    @Override
    public String toString() {
        return "Please check username or password and try again";
    }
}
