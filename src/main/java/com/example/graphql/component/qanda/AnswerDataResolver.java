package com.example.graphql.component.qanda;

import com.example.graphql.datasource.qanda.entity.AnswersEntity;
import com.example.graphql.exception.QAndAAuthenticationException;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Answer;
import com.example.graphql.generated.types.AnswerCreateInput;
import com.example.graphql.generated.types.AnswerVoteInput;
import com.example.graphql.service.command.AnswersCommandService;
import com.example.graphql.service.query.QuestionsQueryService;
import com.example.graphql.service.query.UsersQueryService;
import com.example.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.UUID;

@DgsComponent
public class AnswerDataResolver {

    @Autowired
    private UsersQueryService usersQueryService;

    @Autowired
    private QuestionsQueryService questionsQueryService;

    @Autowired
    private AnswersCommandService answersCommandService;

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.AnswerCreate
    )
    public Answer answerCreate(
            @RequestHeader("authToken") String authToken,
            @InputArgument("answerCreateInput")AnswerCreateInput answerCreateInput
            ){
        var usersEntity = usersQueryService.getUserFromToken(authToken)
                .orElseThrow(QAndAAuthenticationException::new);

        var questionsEntity = questionsQueryService
                .questionDetail(UUID.fromString(answerCreateInput.getQuestionId()))
                .orElseThrow(DgsEntityNotFoundException::new);

        var answersEntity = GraphqlBeanMapper
                .mapInputToEntity(answerCreateInput,usersEntity,questionsEntity);

        var savedAnswersEntity = answersCommandService.saveAnswer(answersEntity);

        return GraphqlBeanMapper.mapToGraphql(savedAnswersEntity);
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.AnswerVote
    )
    public Answer answerVote(
            @RequestHeader("authToken") String authToken,
            @InputArgument("answerVoteInput") AnswerVoteInput answerVoteInput
    ){
        usersQueryService.getUserFromToken(authToken)
                .orElseThrow(QAndAAuthenticationException::new);

        var answerId = UUID.fromString(answerVoteInput.getAnswerId());
        AnswersEntity answersEntity;
        if(answerVoteInput.getIsGood()){
            answersEntity = answersCommandService.upVoteAnswer(answerId).get();
        }else{
            answersEntity = answersCommandService.downVoteAnswer(answerId).get();
        }
        return GraphqlBeanMapper.mapToGraphql(answersEntity);
    }

    @DgsData(
            parentType = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.AnswerVoteChanged
    )
    public Flux<Answer> answerVoteChanged(
            @InputArgument("answerId") String answerId
    ){
        return null;
    }
}
