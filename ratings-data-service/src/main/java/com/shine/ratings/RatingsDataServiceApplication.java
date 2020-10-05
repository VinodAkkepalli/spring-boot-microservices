package com.shine.ratings;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class RatingsDataServiceApplication {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.shine"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfo("ratings-data-service Documentation", 
				"Describes the Ratings data service APIs exposed", 
				"1.0", 
				"Practice Project. Not for production purposes.", 
				new springfox.documentation.service.Contact("Vinod Akkepalli", 
						"https://github.com/VinodAkkepalli", 
						"shining.vinod@gmail.com"), 
				"API License", 
				"https://github.com/VinodAkkepalli", 
				Collections.emptyList());
	}
	 
	
	public static void main(String[] args) {
		SpringApplication.run(RatingsDataServiceApplication.class, args);
	}

}

