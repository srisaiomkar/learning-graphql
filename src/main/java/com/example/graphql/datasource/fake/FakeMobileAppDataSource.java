package com.example.graphql.datasource.fake;

import com.example.graphql.generated.types.Address;
import com.example.graphql.generated.types.Author;
import com.example.graphql.generated.types.MobileApp;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeMobileAppDataSource {

    @Autowired
    private Faker faker;

    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<>();
    @PostConstruct
    public void postConstruct(){
        for(int i =0;i<20;i++){
            List<Address> addresses = new ArrayList<>();
            for(int j =0;j<3;j++){
                var address = Address.newBuilder()
                        .street(faker.address().streetAddress())
                        .city(faker.address().city())
                        .zipcode(faker.address().zipCode())
                        .country(faker.address().country())
                        .build();
                addresses.add(address);
            }
            var author = Author.newBuilder()
                    .name(faker.name().name())
                    .originCountry(faker.country().name())
                    .addresses(addresses)
                    .build();

            var mobileApp = MobileApp.newBuilder()
                    .name(faker.app().name())
                    .author(author)
                    .version(faker.app().version())
                    .platform(getRandomPlatform())
                    .build();

            MOBILE_APP_LIST.add(mobileApp);
        }
    }

    private List<String> getRandomPlatform(){
        switch(ThreadLocalRandom.current().nextInt(10) % 3){
            case 0:
                return List.of("android");
            case 1:
                return List.of("ios");
            default:
                return List.of("android","ios");
        }
    }
}
