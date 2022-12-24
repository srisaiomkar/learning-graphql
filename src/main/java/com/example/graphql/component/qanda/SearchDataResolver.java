package com.example.graphql.component.qanda;

import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Question;
import com.example.graphql.generated.types.SearchableItem;
import com.example.graphql.service.query.AnswersQueryService;
import com.example.graphql.service.query.QuestionsQueryService;
import com.example.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class SearchDataResolver {

    @Autowired
    private QuestionsQueryService questionsQueryService;

    @Autowired
    private AnswersQueryService answersQueryService;

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.QuestionAndAnswerSearch
    )
    public List<SearchableItem> questionAndAnswerSearch(
            @InputArgument("term") String term
    ){
        var result = new ArrayList<SearchableItem>();

        var questions = questionsQueryService.questionsBySearchTerm(term)
                .stream()
                .map(GraphqlBeanMapper::mapToGraphql)
                .collect(Collectors.toList());

        var answers = answersQueryService.findBySearchTerm(term)
                .stream()
                .map(GraphqlBeanMapper::mapToGraphql)
                .collect(Collectors.toList());

        result.addAll(questions);
        result.addAll(answers);

        if(result.isEmpty()){
            throw new DgsEntityNotFoundException("No item with search term" + term);
        }

        result.sort(Comparator.comparing(SearchableItem::getCreatedDateTime).reversed());
        return result;
    }
}
