package com.example.graphql.exception.handler;

import com.example.graphql.exception.QAndAAuthenticationErrorDetail;
import com.example.graphql.exception.QAndAAuthenticationException;
import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class QAndAGraphqlExceptionHandler implements DataFetcherExceptionHandler {
    private final DefaultDataFetcherExceptionHandler defaultHandler =
            new DefaultDataFetcherExceptionHandler();
    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
        var exception = handlerParameters.getException();

        if(exception instanceof QAndAAuthenticationException){
            var graphqlError = TypedGraphQLError.newBuilder()
                    .message(exception.getMessage())
                    .path(handlerParameters.getPath())
//                    .errorType(ErrorType.UNAUTHENTICATED)
                    .errorDetail(new QAndAAuthenticationErrorDetail())
                    .build();
            var result =  DataFetcherExceptionHandlerResult.newResult().error(graphqlError).build();
            return CompletableFuture.completedFuture(result);
        }

        return defaultHandler.handleException(handlerParameters);
    }
}
