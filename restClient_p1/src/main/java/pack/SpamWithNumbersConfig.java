package pack;


import javafx.print.PageOrientation;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class SpamWithNumbersConfig
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean RestTemplateBuilder builder()
    {
        return new RestTemplateBuilder();
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
    public ScheduledTasks startScheduledTasks() {
        return new ScheduledTasks();
    }

    @Bean
    public AuthorizationToken login(RestTemplate restTemplate) throws Exception
    {
        log.info("My bean!");
        UserModelLogin request = new UserModelLogin();
        request.setUsername("test");
        request.setPassword("test");
        log.info(request.toString());
        //http://localhost:8081/api/users/login?username=q&password=w
        AuthorizationToken  token = restTemplate.postForObject(
                "http://localhost:8081/api/users/login",
                request,
                AuthorizationToken.class );
        log.info(token.toString());

        //AuthorizationToken token = restTemplate.postForObject("http://localhost:8081?password=c&email=f&username=d", AuthorizationToken.class);
        return token;
    }

    @Bean
    public HttpHeaders getProperHeader( AuthorizationToken token)
    {
        HttpHeaders result = new HttpHeaders();
        result.set( "Authorization", "Bearer " + token.getToken());
        log.info("SET "+ token.toString());
        return result;
    }

    @Bean
    public String attempt(HttpHeaders headers, RestTemplate restTemplate)
    {
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String>  result = restTemplate.exchange("http://localhost:8081/api/users/current", HttpMethod.GET, entity, String.class );
        log.info("Entity: " + result.toString());
        return result.getBody();
    }


    @Bean
    public Integer variableId(RestTemplate restTemplate, HttpHeaders headers)
    {
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<PageSmallModel> response = restTemplate.exchange(
                "http://localhost:8081/api/variable",
                HttpMethod.GET,
                entity, PageSmallModel.class );
        VariableSmallModel model = response.getBody().getContent().get(0);
        Integer result = model.getId();
        log.info("Variable Id is: " +result.toString());

        return result;
    }
}
