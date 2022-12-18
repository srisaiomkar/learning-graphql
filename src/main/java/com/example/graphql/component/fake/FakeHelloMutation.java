package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeHelloDataSource;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.Hello;
import com.example.graphql.generated.types.HelloInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class FakeHelloMutation {

    @DgsMutation
    public int addHello(HelloInput helloInput){
        var hello = Hello.newBuilder()
                .randomNumber(helloInput.getNumber())
                .text(helloInput.getText())
                .build();
        FakeHelloDataSource.HELLO_LIST.add(hello);
        return FakeHelloDataSource.HELLO_LIST.size();
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.ChangeHello
    )
    public List<Hello> modifyHello(@InputArgument(name = "helloInput") HelloInput input){
        FakeHelloDataSource.HELLO_LIST.stream()
                .filter(h -> h.getRandomNumber() == input.getNumber())
                .forEach(h -> h.setText(input.getText()));
        return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsData(
            parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.DeleteHello
    )
    public int deleteHello(@InputArgument("helloNum") int helloNum){
        FakeHelloDataSource.HELLO_LIST.removeIf(h -> h.getRandomNumber() == helloNum);
        return FakeHelloDataSource.HELLO_LIST.size();
    }
}
