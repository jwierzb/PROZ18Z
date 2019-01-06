package pack;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        RegisterParams request = new RegisterParams("x", "y", "z");
        log.info(request.toString());
        //password=dwa&email=trzy&username=jeden
        // still does not work, I cannot pass the parameters
        AuthorizationToken token = restTemplate.postForObject(
                "http://localhost:8081/api/users/login?username=q&password=w",
                request,
                AuthorizationToken.class );
        log.info(token.toString());
        /*String user = restTemplate.getForObject("http://localhost:8081/api/users/current", String.class);
        log.info(user);*/
        //AuthorizationToken token = restTemplate.postForObject("http://localhost:8081?password=c&email=f&username=d", AuthorizationToken.class);
        return token;
    }
}
