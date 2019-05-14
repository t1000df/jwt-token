package br.com.postalisonline.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .useDefaultResponseMessages(false)		
          .select()                                  
          //.apis(RequestHandlerSelectors.any())    
          .apis(RequestHandlerSelectors.basePackage("br.com.postalisonline.api.controller"))
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo())
          ;                                           
    }
    
   
    private ApiInfo apiInfo() {
    	 
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title ("API REST do Postalis Indentity Manager (IM)")
                .description ("Essa é a API de serviços de gestão de identidade.")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .termsOfServiceUrl("/service.html")
                .version("1.0.0")
                .contact(new Contact("Postalis","www.postalis.org.br", "contato@postalis.org.br"))
                .build();
 
        return apiInfo;
    }
}
