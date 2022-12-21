package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeStockDataSource;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Stock;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsSubscription;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DgsComponent
public class FakeStockDataResolver {

    @Autowired
    private FakeStockDataSource fakeStockDataSource;

//    @DgsSubscription or
    @DgsData(
            parentType = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.RandomStock
    )
    public Publisher<Stock> randomStock(){
        return Flux.interval(Duration.ofSeconds(2)).map(t -> fakeStockDataSource.randomStock());
    }
}
