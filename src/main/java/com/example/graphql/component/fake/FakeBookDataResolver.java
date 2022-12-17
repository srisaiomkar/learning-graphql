package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeBookDataSource;
import com.example.graphql.generated.types.Book;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeBookDataResolver {

    // if method name is same as specified in schema you can use DgsQuery or else use Dgs data
    @DgsData(parentType = "Query",field = "books")
    // Similarly, if argument name in schema is different from argument name in method use @InputArgument
    public List<Book> bookBy(@InputArgument(name = "authorName") Optional<String> nameOfTheAuthor){
        if(nameOfTheAuthor.isEmpty() || StringUtils.isBlank(nameOfTheAuthor.get())){
            return FakeBookDataSource.BOOK_LIST;
        }
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> StringUtils.contains(b.getAuthor().getName(), nameOfTheAuthor.get()))
                .collect(Collectors.toList());
    }
}
