package social_network.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("social_network")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
