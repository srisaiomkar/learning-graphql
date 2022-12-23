package com.example.graphql.component.qanda;

import com.example.graphql.datasource.qanda.entity.UsersEntity;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.*;
import com.example.graphql.service.query.UsersQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.example.graphql.util.GraphqlBeanMapper.mapToGraphql;

@DgsComponent
public class UserDataResolver {

    @Autowired
    private UsersQueryService usersQueryService;

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.Me
    )
    public User userDetails(@RequestHeader("authToken") String authToken){
        var userEntity = usersQueryService.getUserFromToken(authToken);
        return mapToGraphql(userEntity);
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.UserLogin
    )
    public UserResponse loginUser(@InputArgument("userLoginInput")UserLoginInput userLoginInput){
        UsersEntity usersEntity = usersQueryService.loginUser(userLoginInput.getEmail(), userLoginInput.getPassword());
        // for simplicity
        var authToken = AuthToken.newBuilder()
                .token(usersEntity.getEmail())
                .build();
        return UserResponse.newBuilder()
                .user(mapToGraphql(usersEntity))
                .token(authToken)
                .build();
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
            field = DgsConstants.MUTATION.UserActivate
    )
    public UserActivationResponse activateUser(
            @InputArgument("userActivateInput") UserActivateInput userActivateInput
    ){
        return null;
    }
}
