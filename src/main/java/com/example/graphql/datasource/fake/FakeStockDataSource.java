package com.example.graphql.datasource.fake;

import com.example.graphql.generated.types.Stock;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;

@Configuration
public class FakeStockDataSource {

    @Autowired
    private Faker faker;

    public Stock randomStock(){
        return Stock.newBuilder()
                .price(faker.random().nextInt(100,1000))
                .symbol(faker.stock().nyseSymbol())
                .lastTradeDateTime(OffsetDateTime.now())
                .build();
    }
}
