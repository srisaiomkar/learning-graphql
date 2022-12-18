package com.example.graphql.datasource.fake;

import com.example.graphql.generated.types.Cat;
import com.example.graphql.generated.types.Dog;
import com.example.graphql.generated.types.Pet;
import com.example.graphql.generated.types.PetFoodType;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakePetDataSource {
    @Autowired
    private Faker faker;

    public static final List<Pet> PET_LIST = new ArrayList<>();

    @PostConstruct
    public void postConstruct(){
        for(int i =0;i<10;i++){
            switch(i%2){
                case 0:
                    var dog = Dog.newBuilder()
                            .name(faker.dog().name())
                            .breed(faker.dog().breed())
                            .foodType(PetFoodType.OMNIVORE)
                            .coatLength(faker.dog().coatLength())
                            .build();
                    PET_LIST.add(dog);
                case 1:
                    var cat = Cat.newBuilder()
                            .name(faker.cat().name())
                            .breed(faker.cat().breed())
                            .foodType(PetFoodType.CARNIVORE)
                            .registry(faker.cat().registry())
                            .build();
                    PET_LIST.add(cat);
            }
        }
    }
}
