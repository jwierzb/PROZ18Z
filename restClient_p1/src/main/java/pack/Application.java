package pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception
    {
        return args -> {
            Quote quote = restTemplate.getForObject(
                    "http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
            log.info(quote.toString());
        };
    }

    @Bean
    public CommandLineRunner register(RestTemplate restTemplate) throws Exception
    {

        return args -> {
            log.info("My bean!");
            /*HttpEntity<RegisterParams> request = new HttpEntity<>(new RegisterParams("a", "b", "c"));
            AuthorizationToken token = restTemplate.postForObject(
                    "http://localhost:8081/api/users/register",
                    request,
                    AuthorizationToken.class );
            log.info(token.toString());*/
            //AuthorizationToken token = restTemplate.postForObject("http://localhost:8081?password=c&email=f&username=d", AuthorizationToken.class);
        };
    }
}