/**
 * @author Vaibhav Borkar
 * @explanation This class provide the swagger configuration.
 */

package com.enotes.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		OpenAPI openAPI = new OpenAPI();
		Info info = new Info();
		info.setTitle("ENotes API");
		info.setDescription("ENotes API Service Documentation");
		info.setVersion("0.0.1");
		info.setTermsOfService("http://vaibhav.com");

		Contact contact = new Contact();
		contact.setEmail("businesswithchitti@gmail.com");
		contact.setName("Vaibhav Borkar");
		contact.setUrl(null);

		info.setContact(contact);

		Server dev = new Server();
		dev.setDescription("dev");
		dev.setUrl("http://localhost:8080");

		Server test = new Server();
		test.setDescription("test");
		test.setUrl("http://localhost:8081");

		Server uat = new Server();
		uat.setDescription("uat");
		uat.setUrl("http://localhost:8082");
		
		Server prod = new Server();
		prod.setDescription("prod");
		prod.setUrl("http://localhost:8083");

		List<Server> serverList = List.of(dev, test,uat, prod);

		SecurityScheme securityScheme = new SecurityScheme();
		securityScheme.name("Authorization");
		securityScheme.scheme("bearer");
		securityScheme.type(Type.HTTP);
		securityScheme.bearerFormat("JWT");
		securityScheme.in(In.HEADER);

		Components component = new Components();
		component.addSecuritySchemes("Token", securityScheme);
		
		openAPI.setServers(serverList);
		openAPI.setInfo(info);
		openAPI.setComponents(component);
		openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));
		
		return openAPI;
	}
}
