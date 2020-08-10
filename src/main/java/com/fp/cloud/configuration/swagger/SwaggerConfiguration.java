package com.fp.cloud.configuration.swagger;

import com.fp.cloud.configuration.oauth.Oauth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Autowired
    Oauth2Properties properties;

    @Bean
    public Docket api() {
        //return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.push.app.main.controller"))
//                .paths(PathSelectors.any())
//                .build();
//        List<ResponseMessage> list = new java.util.ArrayList<>();
//        list.add(new ResponseMessageBuilder().code(500).message("500 message")
//                .responseModel(new ModelRef("Result")).build());
//        list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
//                .responseModel(new ModelRef("Result")).build());
//        list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
//                .responseModel(new ModelRef("Result")).build());

        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.fp.cloud.main.controller"))
                .paths(PathSelectors.any()).build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/")
//                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
//                .globalResponseMessage(RequestMethod.GET, list)
//                .globalResponseMessage(RequestMethod.POST, list);
    }

    private OAuth securityScheme() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = new ArrayList();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(properties.getTokenUrl());

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

//    @Bean
//    public SecurityConfiguration securityInfo() {
//        SecurityConfiguration(properties.getClientId(), String clientSecret, String realm, String appName, String scopeSeparator, Map<String, Object> additionalQueryStringParams, Boolean useBasicAuthenticationWithAccessCodeGrant)
//        return new SecurityConfiguration(properties.getClientId(), properties.getClientSecret(), "", "", "", ApiKeyVehicle.HEADER, "", " ");
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Push Notification API").description("")
                .termsOfServiceUrl("http://localhost:8080/api")
                .contact(new Contact("First Payment Indonesia", "https://www.swipepay.co.id/", "sales@firstpayment.co.id"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}
