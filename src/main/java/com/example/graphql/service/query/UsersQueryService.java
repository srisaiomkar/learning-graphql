package com.example.graphql.service.query;

import com.example.graphql.datasource.qanda.entity.UsersEntity;
import com.example.graphql.datasource.qanda.repository.UsersRepository;
import com.example.graphql.generated.types.AuthToken;
import com.example.graphql.generated.types.UserResponse;
import com.example.graphql.util.PasswordMatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersQueryService {
    @Autowired
    private UsersRepository usersRepository;

    public UsersEntity loginUser(String email, String password){
        var user = usersRepository.findByEmailIgnoreCase(email);

        if(user.isEmpty()
        || !PasswordMatcherUtil.isPasswordMatch(password,user.get().getPassword())){
            throw new IllegalArgumentException();
        }

        return user.get();
    }

    public UsersEntity getUserFromToken(String token){
        // for simplicity used email
        return usersRepository.findByEmailIgnoreCase(token).get();
    }
}
