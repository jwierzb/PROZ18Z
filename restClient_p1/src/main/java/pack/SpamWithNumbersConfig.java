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
        RegisterParams request = new RegisterParams("x", "y", "z");
        log.info(request.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("username", "q");
        json.put("password", "w");

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), headers);

        //http://localhost:8081/api/users/login?username=q&password=w
        // still does not work, I cannot pass the parameters
        String  token = restTemplate.postForObject(
                "http://localhost:8081/api/users/login",
                httpEntity,
                String.class );
        log.info(token.toString());
        /*String user = restTemplate.getForObject("http://localhost:8081/api/users/current", String.class);
        log.info(user);*/
        //AuthorizationToken token = restTemplate.postForObject("http://localhost:8081?password=c&email=f&username=d", AuthorizationToken.class);
        AuthorizationToken x = new AuthorizationToken();
        return x;
    }
}
