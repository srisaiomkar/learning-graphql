package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeMobileAppDataSource;
import com.example.graphql.generated.DgsConstants;
import com.example.graphql.generated.types.MobileApp;
import com.example.graphql.generated.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeMobileAppDataResolver {

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.MobileApps
    )
    public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter") Optional<MobileAppFilter> filter) {
        if(filter.isEmpty()){
            return FakeMobileAppDataSource.MOBILE_APP_LIST;
        }

        return FakeMobileAppDataSource.MOBILE_APP_LIST.stream()
                .filter(app -> this.filterAppData(app,filter.get()))
                .collect(Collectors.toList());
    }

    private boolean filterAppData(MobileApp app, MobileAppFilter filter) {
        if (!StringUtils.containsIgnoreCase(
                app.getName(),
                StringUtils.defaultIfBlank(filter.getName(),StringUtils.EMPTY)
        )) return false;

        if(!StringUtils.containsIgnoreCase(
                app.getVersion(),
                StringUtils.defaultIfBlank(filter.getVersion(), StringUtils.EMPTY)
        )) return false;

        if(StringUtils.isNotBlank(filter.getPlatform()) &&
            !app.getPlatform().contains(filter.getPlatform().toLowerCase())
        ) return false;

        if(filter.getAuthorFilter() != null && !StringUtils.containsIgnoreCase(
                app.getAuthor().getName(),
                StringUtils.defaultIfBlank(filter.getAuthorFilter().getName(),StringUtils.EMPTY))
        ) return false;

        if(!app.getReleasedDate().isAfter(
                Optional.ofNullable(filter.getReleasedAfter()).orElse(LocalDate.MIN)
            )
        ) return false;

        if(filter.getAppUrl() != null && !StringUtils.equalsIgnoreCase(
                app.getAppUrl().toString(),
                StringUtils.defaultIfBlank(filter.getAppUrl().toString(),StringUtils.EMPTY)
        )) return false;

        if( app.getNumOfDownloads() <
                Optional.ofNullable(filter.getMinDownloads()).orElse(0)
        ){
            return false;
        }
        if(filter.getCategory() != null && !app.getCategory().equals(filter.getCategory())){
            return false;
        }

        return true;
    }
}
