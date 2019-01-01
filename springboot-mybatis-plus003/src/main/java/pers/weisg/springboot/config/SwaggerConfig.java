package pers.weisg.springboot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2018/12/30
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name ="enabled" ,prefix = "swagger",havingValue = "true",matchIfMissing = false)
//@Profile({"dev","test"})
public class SwaggerConfig{
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("pers.weisg.springboot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("weisg81个人CSND地址")
                .description("原文地址链接：https://blog.csdn.net/weisg81")
                .termsOfServiceUrl("https://blog.csdn.net/weisg81")
                .contact(new Contact("weisangeng","https://blog.csdn.net/weisg81","weisangeng@126.com"))
                .version("1.0")
                .build();
    }

    /*@Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api-docs","/swagger-ui.html");
    }*/
}
