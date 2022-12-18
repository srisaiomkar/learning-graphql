package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeBookDataSource;
import com.example.graphql.datasource.fake.FakeMobileAppDataSource;
import com.example.graphql.generated.types.SmartSearchResult;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class SmartSearchDataResolver {

    @DgsQuery
    public List<SmartSearchResult> smartSearch(Optional<String> searchTerm) {
        var result = new ArrayList<SmartSearchResult>();
        if(searchTerm.isEmpty()){
            result.addAll(FakeBookDataSource.BOOK_LIST);
            result.addAll(FakeMobileAppDataSource.MOBILE_APP_LIST);
        }else{
            FakeBookDataSource.BOOK_LIST.stream()
                    .filter(book -> StringUtils.containsIgnoreCase(
                            book.getTitle(),searchTerm.get())
                    ).forEach(result::add);

            FakeMobileAppDataSource.MOBILE_APP_LIST.stream()
                    .filter(app -> StringUtils.containsIgnoreCase(
                            app.getName(),searchTerm.get())
                    ).forEach(result::add);
        }
        return result;
    }
}
