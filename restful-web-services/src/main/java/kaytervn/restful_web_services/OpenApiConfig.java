package kaytervn.restful_web_services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	public final Contact DEFAULT_CONTACT = new Contact().name("Kayter").email("kienductrong@gmail.com").url("github.com/kaytervn");
	public final Info DEFAULT_INFO = new Info().title("Awesome API Documentation").contact(DEFAULT_CONTACT);

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI().info(DEFAULT_INFO);
	}
}
