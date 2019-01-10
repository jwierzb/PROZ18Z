package com.boraji.tutorial.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
@Slf4j
public class MainController {
    @Autowired
    HttpHeaders authorizationHeaders;
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    HttpURLConnection establish(String urlAddress) throws IOException {
        String port = "http://localhost:8081";
        URL url = new URL(port + urlAddress);
        HttpURLConnection result = (HttpURLConnection) url.openConnection();
        // TIMEOUTS
        result.setConnectTimeout(5000);
        result.setReadTimeout(5000);

        //result.setRequestMethod(method);
        result.setRequestProperty("Content-Type", "application/json");
        return result;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    public String sayHello() {
        return "another";
    }

    @GetMapping("/register")
    public String registerScreen() {
        return "register_screen";
    }

    @PostMapping("/register")
    public String logInRequest(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("email") String email, Model model) {
        log.info("Register request with params: " + name + " " + password + " " + email);
        UserModelRegister body = new UserModelRegister(name, password, email);

        String response = restTemplate.postForObject("http://localhost:8081/api/users/new", body, String.class);

        model.addAttribute("name", response);
        return "hello";
    }

    @GetMapping("/login")
    public String loginScreen() {
        return "login_screen";
    }

    @PostMapping("/login")
    public String logInRequest(@RequestParam("name") String name, @RequestParam("password") String password, Model model) {
        log.info("Login request with params: " + name + " " + password);
        UserModelLogin body = new UserModelLogin(name, password);

        RestTemplate restTemplate = new RestTemplate();

        AuthorizationToken response = restTemplate.postForObject("http://localhost:8081/api/users/login", body, AuthorizationToken.class);
        authorizationHeaders.set("Authorization", "Bearer " + response.getToken());

        model.addAttribute("name", name);
        model.addAttribute("token", "Bearer " + response.getToken());
        return "logged_home";
    }

    @GetMapping("/devices")
    public String showDevices(Model model) {
        HttpEntity<String> entity = new HttpEntity<>("parameters", authorizationHeaders);
        ResponseEntity<List<DeviceBigModel>> response = restTemplate.exchange(
                "http://localhost:8081/api/devices",
                HttpMethod.GET,
                entity, new ParameterizedTypeReference<List<DeviceBigModel>>() {});
        ResponseEntity<String> testResponse = restTemplate.exchange(
                "http://localhost:8081/api/devices",
                HttpMethod.GET,
                entity, String.class);

        model.addAttribute("name", testResponse.getBody());
        model.addAttribute("second_name", response.getBody().toString());
        model.addAttribute("devices", response.getBody());
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
        DeviceModel deviceModel = new DeviceModel(name, enabled, tags, description);
        log.info("Trying to create device: " + deviceModel.toString());
        HttpEntity<DeviceModel> entity = new HttpEntity<>(deviceModel, authorizationHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8081/api/devices",
                HttpMethod.POST,
                entity, String.class);
        model.addAttribute("name", response.getBody() );
        return "logged_home";
    }

    @GetMapping("/devices/{id}")
    public String showSingleDevice(Model model)
    {
        return "hello";
    }


    @GetMapping("/api/variables")
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
    }

}