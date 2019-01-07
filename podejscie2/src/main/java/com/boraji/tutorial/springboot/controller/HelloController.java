package com.boraji.tutorial.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import model.UserModelRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class HelloController
{
    HttpURLConnection establish( String urlAddress ) throws IOException
    {
        String port = "http://localhost:8081";
        URL url = new URL(port + urlAddress);
        HttpURLConnection result = (HttpURLConnection)url.openConnection();
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
    public String sayHello()
    {
        return "another";
    }

    @GetMapping("/register")
    public String registerScreen()
    {
        return "register_screen";
    }

    @PostMapping("/register")
    public String logInRequest( @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("email") String email, Model model )
    {
        log.info("Register request with params: " + name + " " + password + " " + email );
        UserModelRegister body = new UserModelRegister(name, password, email);

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.postForObject("http://localhost:8081/api/users/register", body, String.class );
        
        model.addAttribute("name", response);
        return "hello";
    }

    @PostMapping("/login")
    public String logInRequest( @RequestParam("name") String name, @RequestParam("password") String password, Model model )
    {
        HttpURLConnection con;
        StringBuffer content = new StringBuffer(); // do tego zczyta dane
        {
            try {

                con = establish( "/api/users/login");

                Map<String, String> parameters = new HashMap<>();
                parameters.put("username", name);
                parameters.put("password", password);
                // putting parameters
                con.setDoOutput(true);

                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                // create a reader
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                // read everything
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                System.out.println(content.toString());

                name = content.toString();
                in.close();
                con.disconnect();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        model.addAttribute("name", name);
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