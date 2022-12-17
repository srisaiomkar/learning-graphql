package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeBookDataSource;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Book;
import com.example.graphql.generated.types.ReleasedHistory;
import com.example.graphql.generated.types.ReleasedHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
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


    // Using Gradle plugin data resolver plugin's generate example to generate teh skeleton
    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksByReleaseYear
    )
    public List<Book> getBooksByReleaseYear(DataFetchingEnvironment dataFetchingEnvironment) {
        var inputMap = (Map<String,Object>)dataFetchingEnvironment.getArgument("releasedHistoryInput");
        var releasedInput = ReleasedHistoryInput.newBuilder()
                .year((int)inputMap.get(DgsConstants.RELEASEDHISTORYINPUT.Year))
                .hasPrintedEdition((boolean) inputMap.get(DgsConstants.RELEASEDHISTORYINPUT.HasPrintedEdition))
                .build();

        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> matchesReleaseHistory(releasedInput, b.getReleasedHistory()))
                .collect(Collectors.toList());
    }

    private Boolean matchesReleaseHistory(ReleasedHistoryInput input, ReleasedHistory history){
        return input.getHasPrintedEdition() == history.getHasPrintedEdition()
                && input.getYear() == history.getYear();
    }
}
