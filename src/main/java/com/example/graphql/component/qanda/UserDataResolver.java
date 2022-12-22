package com.example.graphql.component.qanda;

import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.Me
    )
    public User userDetails(@RequestHeader("authToken") String authToken){
        return null;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserCreate
    )
    public UserResponse createUser(@InputArgument("userCreateInput")UserCreateInput userCreateInput){
        return null;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserLogin
    )
    public UserResponse loginUser(@InputArgument("userLoginInput")UserLoginInput userLoginInput){
        return null;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UserActivate
    )
    public UserActivationResponse activateUser(
            @InputArgument("userActivateInput") UserActivateInput userActivateInput
    ){
        return null;
    }
}
