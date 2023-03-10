package com.example.graphql.component.qanda;

import com.example.graphql.exception.QAndAAuthenticationException;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Question;
import com.example.graphql.generated.types.QuestionCreateInput;
import com.example.graphql.service.command.QuestionsCommandService;
import com.example.graphql.service.query.QuestionsQueryService;
import com.example.graphql.service.query.UsersQueryService;
import com.example.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.graphql.util.GraphqlBeanMapper.mapToGraphql;

@DgsComponent
public class QuestionDataResolver {

    @Autowired
    private UsersQueryService usersQueryService;

    @Autowired
    private QuestionsCommandService questionsCommandService;

    @Autowired
    private QuestionsQueryService questionsQueryService;
    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.QuestionLatestList
    )
    public List<Question> questionLatestList(){
        return questionsQueryService.questionLatestList().stream()
                .map(GraphqlBeanMapper::mapToGraphql)
                .collect(Collectors.toList());
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.QuestionDetails
    )
    public Question questionDetails(
            @InputArgument("id") String id
    ){
        var uuid = UUID.fromString(id);
        var questionEntity = questionsQueryService.questionDetail(uuid)
                .orElseThrow(DgsEntityNotFoundException::new);
        return mapToGraphql(questionEntity);
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.QuestionCreate
    )
    public Question questionCreate(
            @RequestHeader("authToken") String authToken,
            @InputArgument("questionCreateInput")QuestionCreateInput questionCreateInput
            ){
        var usersEntity = usersQueryService.getUserFromToken(authToken)
                .orElseThrow(QAndAAuthenticationException::new);
        var questionsEntity = GraphqlBeanMapper.mapInputToEntity(questionCreateInput, usersEntity);
        var savedQuestionsEntity = questionsCommandService.createQuestion(questionsEntity);
        return mapToGraphql(savedQuestionsEntity);
    }

    @DgsData(
            parentType = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.QuestionAdded
    )
    public Flux<Question> questionAdded(
            @RequestHeader("authToken") String authToken
    ){
        return null;
    }

}
