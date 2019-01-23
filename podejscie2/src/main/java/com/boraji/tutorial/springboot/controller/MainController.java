package com.boraji.tutorial.springboot.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jws.WebParam;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor // konstruktor dziala jak Autowired
public class MainController {
    private final HttpHeaders authorizationHeaders;
    private final ApiClientService apiClientService;

    // robię klase service, która bedzie polem

    @RequestMapping("/")
    public String index( Model model )
    {
        model.addAttribute("message", "Project PROZ 2018Z");
        return "index";
    }

    @GetMapping("/register")
    public String registerScreen() {
        return "register_screen";
    }

    @PostMapping("/register")
    public String logInRequest(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("email") String email, Model model) {
        log.info("Register request with params: " + name + " " + password + " " + email);

        String response = apiClientService.registerUser(name, password, email);

        model.addAttribute("message", "User registered");
        return "index";
    }

    @GetMapping("/login")
    public String loginScreen() {
        return "login_screen";
    }

    @GetMapping("/logout")
    public String logout( Model model )
    {
        apiClientService.logout();
        model.addAttribute("message", "You have been logged out");
        return "index";
    }

    @PostMapping("/login")
    public String logInRequest(@RequestParam("name") String name, @RequestParam("password") String password, Model model) {
        log.info("Login request with params: " + name + " " + password);
        UserModelLogin body = new UserModelLogin(name, password);

        AuthorizationToken response = apiClientService.login(name, password);

        model.addAttribute("name", name);
        model.addAttribute("token", "Bearer " + response.getToken());
        return "logged_home";
    }

    @GetMapping("/devices")
    public String showDevices(Model model) {
        HttpEntity<String> entity = new HttpEntity<>("parameters", authorizationHeaders);

        List<DeviceBigModel> devices = apiClientService.getDeviceList();

        model.addAttribute("name", devices.toString());
        model.addAttribute("devices", devices);
        return "devices";
    }

    @RequestMapping("/createdevice")
    public String createDeviceScreen(Model model)
    { return "create_device"; }

    @PostMapping("/devices")
    public String createDevice(@RequestParam(name = "name") String name,
                               @RequestParam(name = "enabled", defaultValue = "true") boolean enabled,
                               @RequestParam(name = "tags", defaultValue = "") String tags,
                               @RequestParam(name = "description", defaultValue = "") String description,
                               Model model)
    {
        String response = apiClientService.createDevice(name, enabled, tags, description);
        model.addAttribute("name", "Device created" );
        return "logged_home";
    }

    @GetMapping("/devices/{id}")
    public String showSingleDevice(@PathVariable Integer id, Model model)
    {
        DeviceBigModel device = apiClientService.getSingleDevice(id);
        model.addAttribute("device", device);
        return "single_device";
    }

    @GetMapping("/devices/{id}/variables")
    public String showVariablesOfDevice( @PathVariable Integer id, Model model )
    {
        List<VariableBigModel> variables = apiClientService.getDeviceVariables(id);
        model.addAttribute("name", variables.toString());
        model.addAttribute("variables", variables);
        return "variables";
    }

    @GetMapping("/variables/{id}")
    public String showVariableHistory(@PathVariable Integer id, Model model)
    {
        List<ValueModel> values = apiClientService.getVariableValues(id);
        model.addAttribute("values", values);
        return "variable_values";
    }


    /*@GetMapping("/api/variables")
    public String readVariables()
    {
        URL url;
        HttpURLConnection con;
        StringBuffer content = new StringBuffer(); // do tego zczyta dane
        {
            try {
                con = establish( "/api/variables" );
                // create a reader
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                // read everything
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                System.out.println(content.toString());

                in.close();
                con.disconnect();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return "something";
    }

    @GetMapping("/api/variables/{id}")
    public String readVariable( @PathVariable String id )
    {
        URL url;
        HttpURLConnection con;
        StringBuffer content = new StringBuffer(); // do tego zczyta dane
        {
            try {
                con = establish( "/api/variables/{id}" );


                // create a reader
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                // read everything
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                System.out.println(content.toString());

                in.close();
                con.disconnect();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return "something";
    }*/

}