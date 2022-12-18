package com.example.graphql.component.fake;

import com.example.graphql.generated.DgsConstants;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@DgsComponent
public class HeaderDemoDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field =  DgsConstants.QUERY.HeaderDemo
    )
    public String headerDemo(
            @RequestHeader(value = "mandatoryHeader",required = true) String mandatoryHeader,
            @RequestHeader(value = "optionalHeader",required = false) String optionalHeader,
            @RequestParam(value = "mandatoryQueryParam",required = true) String mandatoryQueryParam,
            @RequestHeader(value = "optionalQueryParam",required = false) String optionalQueryParam
    ){
        var sb = new StringBuilder();
        sb.append("mandatoryHeader " + mandatoryHeader);
        sb.append(" ");
        sb.append("optionalHeader " + optionalHeader);
        sb.append(" ");
        sb.append("mandatoryQueryParam " + mandatoryQueryParam);
        sb.append(" ");
        sb.append("optionalQueryParam " + optionalQueryParam);
        return sb.toString();
    }
}
