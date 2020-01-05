package com.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //springboot里专门加载配置文件的标签
@EnableSwagger2 //基本上是固定写法
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/") //配置整个的访问路径
                .select() //选择路径
                .paths(PathSelectors.regex("/.*")) //加上一个选择器，正则匹配在业务包里应该访问的哪些方法路径
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("我的接口文档")
                .contact(new Contact("dazhou","","2574711439@qq.com"))
                .description("这是我的swaggerui生成的接口文档")
                .version("1.0.0.0")
                .build();
    }
}
