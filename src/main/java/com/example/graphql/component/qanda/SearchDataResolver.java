package com.example.graphql.component.qanda;

import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Question;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class SearchDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.QuestionAndAnswerSearch
    )
    public List<Question> questionAndAnswerSearch(
            @InputArgument("term") String term
    ){
        return null;
    }
}
