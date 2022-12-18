package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakePetDataSource;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Cat;
import com.example.graphql.generated.types.Dog;
import com.example.graphql.generated.types.Pet;
import com.example.graphql.generated.types.PetFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakePetDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.Pets
    )
    public List<Pet> getPets(@InputArgument(name = "petFilter") Optional<PetFilter> filter){
        if(filter.isEmpty()){
            return FakePetDataSource.PET_LIST;
        }

        return FakePetDataSource.PET_LIST.stream()
                .filter(pet -> this.matchFilter(pet, filter.get()))
                .collect(Collectors.toList());
    }

    private boolean matchFilter(Pet pet, PetFilter petFilter) {
        if(StringUtils.isBlank(petFilter.getName())){
            return true;
        }
        if(petFilter.getName().equalsIgnoreCase(Dog.class.getSimpleName())){
            return pet instanceof Dog;
        } else if (petFilter.getName().equalsIgnoreCase(Cat.class.getSimpleName())){
            return pet instanceof Cat;
        }else {
            return false;
        }
    }
}
