package com.boraji.tutorial.springboot.controller;

import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiClientService
{
    private final RestTemplate restTemplate;
    private final HttpHeaders authorizationHeaders;
    private final String mainURL;

    // zrobic testy metod tej klasy,

    public String registerUser(final String name, final String password, final String email){
        UserModelRegister body = new UserModelRegister(name, password, email);

        return restTemplate.postForObject(mainURL + "/api/users/new", body, String.class);

    }

    public List<DeviceBigModel> getDeviceList()
    {
        HttpEntity<String> entity = new HttpEntity<>("parameters", authorizationHeaders);
        ResponseEntity<List<DeviceBigModel>> response = restTemplate.exchange(
                mainURL + "/api/devices",
                HttpMethod.GET,
                entity, new ParameterizedTypeReference<List<DeviceBigModel>>() {});
        return response.getBody();
    }

    public DeviceBigModel getSingleDevice( Integer id)
    {
        HttpEntity<String> entity = new HttpEntity<>("parameters", authorizationHeaders);
        ResponseEntity<DeviceBigModel> response = restTemplate.exchange(
                mainURL + "/api/devices/" + id.toString(),
                HttpMethod.GET,
                entity, new ParameterizedTypeReference<DeviceBigModel>() {});
        return response.getBody();
    }

    public String createDevice(final String name,
                             final boolean enabled,
                             final String tags,
                             final String description)
    {
        DeviceModel deviceModel = new DeviceModel(name, enabled, tags, description);
        HttpEntity<DeviceModel> entity = new HttpEntity<>(deviceModel, authorizationHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                mainURL + "/api/devices",
                HttpMethod.POST,
                entity, String.class);
        return response.getBody();
    }

    public AuthorizationToken login(final String name,
                                    final String password)
    {
        UserModelLogin body = new UserModelLogin(name, password);

        AuthorizationToken response = restTemplate.postForObject(mainURL + "/api/users/login", body, AuthorizationToken.class);
        authorizationHeaders.set("Authorization", "Bearer " + response.getToken());
        return response;
    }

    public List<VariableBigModel> getDeviceVariables(final Integer id)
    {
        HttpEntity<String> entity = new HttpEntity<>("parameters", authorizationHeaders);
        ResponseEntity<List<VariableBigModel>> response = restTemplate.exchange(
                mainURL + "/api/devices/" + id.toString() + "/variables",
            HttpMethod.GET,
            entity, new ParameterizedTypeReference<List<VariableBigModel>>() {});
        return response.getBody();
    }

    public List<ValueModel> getVariableValues(final Integer id)
    {
        HttpEntity<String> entity = new HttpEntity<>("parameters", authorizationHeaders);
        log.info("Get history attempt");
        ResponseEntity<PageSmallModel> response = restTemplate.exchange(
                mainURL +"/api/variable/" + id.toString() + "/values",
                HttpMethod.GET,
                entity, PageSmallModel.class );
        log.info("Get history successfull" + response.toString());
        return response.getBody().getContent();
    }



    public void logout()
    {
        authorizationHeaders.clear();
    }
}
