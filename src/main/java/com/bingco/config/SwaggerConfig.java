package com.bingco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.bingco.controller"})
public class SwaggerConfig {

    @Bean
   public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("bingco", "http://mvnrepository.com/", "bingco.zn@gmail.com");
        return new ApiInfoBuilder()
                .title("前台API接口")
                .description("前台API接口")
                .contact(contact)
                .version("1.1.0")
                .build();
    }

}
