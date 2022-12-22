package com.example.graphql.component.qanda;

import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Answer;
import com.example.graphql.generated.types.AnswerCreateInput;
import com.example.graphql.generated.types.AnswerVoteInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class AnswerDataResolver {

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.AnswerCreate
    )
    public Answer answerCreate(
            @RequestHeader("authToken") String authToken,
            @InputArgument("answerCreateInput")AnswerCreateInput answerCreateInput
            ){
        return null;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.AnswerVote
    )
    public Answer answerVote(
            @RequestHeader("authToken") String authToken,
            @InputArgument("answerVoteInput") AnswerVoteInput answerVoteInput
    ){
        return null;
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
