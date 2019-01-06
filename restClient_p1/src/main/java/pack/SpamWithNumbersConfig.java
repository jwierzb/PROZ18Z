package pack;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpamWithNumbersConfig
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    //private static final RestTemplateBuilder buider = new RestTemplateBuilder();

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
        LoginParams request = new LoginParams("q", "w");
        log.info(request.toString());


        //http://localhost:8081/api/users/login?username=q&password=w
        // still does not work, I cannot pass the parameters
        String  token = restTemplate.postForObject(
                "http://localhost:8081/api/users/login",
                request,
                String.class );
        log.info(token.toString());

        //AuthorizationToken token = restTemplate.postForObject("http://localhost:8081?password=c&email=f&username=d", AuthorizationToken.class);
        AuthorizationToken x = new AuthorizationToken();
        return x;
    }
}
