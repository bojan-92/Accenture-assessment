package com.accenture.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis( RequestHandlerSelectors.basePackage( "com.accenture.api" ) )
        .paths(PathSelectors.any())
        .build()
        .securityContexts(Lists.newArrayList(securityContext()))
        .apiInfo(apiInfo());
  }

  @Bean
  SecurityContext securityContext() {
    return SecurityContext.builder()
        .forPaths(PathSelectors.any())
        .build();
  }

//  List<SecurityReference> defaultAuth() {
//    AuthorizationScope authorizationScope
//        = new AuthorizationScope("global", "accessEverything");
//    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//    authorizationScopes[0] = authorizationScope;
//    return Lists.newArrayList(
//        new SecurityReference("JWT", authorizationScopes));
//  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Spring Boot REST API")
        .description("Payments Api")
        .termsOfServiceUrl("localhost")
        .version("1.0")
        .contact(new Contact("Bojan Trifkovic", "", "bojantrifkovic92@gmail.com"))
        .build();
  }
}