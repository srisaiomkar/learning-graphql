package com.example.graphql.datasource.fake;

import com.example.graphql.generated.types.Address;
import com.example.graphql.generated.types.Author;
import com.example.graphql.generated.types.Book;
import com.example.graphql.generated.types.ReleasedHistory;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakeBookDataSource {

    @Autowired
    private Faker faker;

    public static final List<Book> BOOK_LIST = new ArrayList<>();

    @PostConstruct
    public void postConstruct(){
        for(int i =0;i<20;i++){
            List<Address> addressList = new ArrayList<>();
            for(int j=0;j<3;j++){
                var address = Address.newBuilder()
                        .city(faker.address().city())
                        .zipcode(faker.address().zipCode())
                        .street(faker.address().streetAddress())
                        .country(faker.address().country())
                        .build();
                addressList.add(address);
            }

            var author = Author.newBuilder()
                    .name(faker.name().name())
                    .originCountry(faker.address().country())
                    .addresses(addressList)
                    .build();

            var releasedHistory = ReleasedHistory.newBuilder()
                    .releasedCountry(faker.address().country())
                    .year(faker.number().numberBetween(2019,2022))
                    .hasPrintedEdition(faker.bool().bool())
                    .build();

            var book = Book.newBuilder()
                    .title(faker.book().title())
                    .author(author)
                    .publisher(faker.name().name())
                    .releasedHistory(releasedHistory)
                    .build();

            BOOK_LIST.add(book);
        }
    }
}
