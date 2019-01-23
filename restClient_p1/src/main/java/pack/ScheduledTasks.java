package pack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    HttpHeaders headers;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Integer variableId;

    @Autowired
    String mainURL;

    @Scheduled(fixedRate = 5000)
    public void pushRandomNumber()
    {
        Random random = new Random();
        Integer n = random.nextInt(9);
        log.info("Random number for this second is " + n );
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(mainURL + "/api/variable/" + variableId.toString() + "?value="+ n.toString(),
                HttpMethod.POST,
                entity,
                String.class );
        log.info("Entity: " + result.getBody().toString());
    }
}
