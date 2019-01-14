package com.boraji.tutorial.springboot.controller;

import model.DeviceBigModel;
import model.ValueModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class ApiClientServiceTest
{


    @Test
    public void TestCase1()
    {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        ApiClientService service = new ApiClientService(restTemplate, new HttpHeaders(), "http://localhost:8081");

        mockRestServiceServer.expect(requestTo("http://localhost:8081/api/users/new"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());

        service.registerUser("w", "q", "a@asd");

        mockRestServiceServer.verify();
    }

    @Test
    public void TestCase2()
    {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        ApiClientService service = new ApiClientService(restTemplate, new HttpHeaders(), "http://localhost:8081");

        mockRestServiceServer.expect(requestTo("http://localhost:8081/api/devices"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[{\"id\": 21, \"deviceName\": \"test\", \"enabled\": true, \"tags\": \"test\", \"description\": \"test\"}]", MediaType.APPLICATION_JSON));

        List<DeviceBigModel> result = service.getDeviceList();

        mockRestServiceServer.verify();
        DeviceBigModel model = result.get(0);
        Assert.assertEquals((int)model.getId(), 21);
        Assert.assertEquals(model.getDeviceName(), "test");
        Assert.assertEquals(model.getTags(), "test");
        Assert.assertEquals(model.getDescription(), "test");
    }

    @Test
    public void TestCase3()
    {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        ApiClientService service = new ApiClientService(restTemplate, new HttpHeaders(), "http://localhost:8081");

        mockRestServiceServer.expect(requestTo("http://localhost:8081/api/devices/21"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\": 21, \"deviceName\": \"test\", \"enabled\": true, \"tags\": \"test\", \"description\": \"test\"}", MediaType.APPLICATION_JSON));

        DeviceBigModel model = service.getSingleDevice(21);

        mockRestServiceServer.verify();
        Assert.assertEquals((int)model.getId(), 21);
        Assert.assertEquals(model.getDeviceName(), "test");
        Assert.assertEquals(model.getTags(), "test");
        Assert.assertEquals(model.getDescription(), "test");
    }

    @Test
    public void TestCase4()
    {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        ApiClientService service = new ApiClientService(restTemplate, new HttpHeaders(), "http://localhost:8081");

        mockRestServiceServer.expect(requestTo("http://localhost:8081/api/devices"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Content-type", "application/json;charset=UTF-8"))
                .andRespond(withSuccess());

        service.createDevice("test", true, "test", "test");

        mockRestServiceServer.verify();
    }

    @Test
    public void TestCase5()
    {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        ApiClientService service = new ApiClientService(restTemplate, new HttpHeaders(), "http://localhost:8081");

        mockRestServiceServer.expect(requestTo("http://localhost:8081/api/users/login"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"token\": \"asdf\"}", MediaType.APPLICATION_JSON));

        service.login("test", "test");

        mockRestServiceServer.verify();
    }

    @Test
    public void TestCase6()
    {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        ApiClientService service = new ApiClientService(restTemplate, new HttpHeaders(), "http://localhost:8081");

        mockRestServiceServer.expect(requestTo("http://localhost:8081/api/devices/21/variables"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        service.getDeviceVariables(21);

        mockRestServiceServer.verify();
    }

    @Test
    public void TestCase7()
    {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        ApiClientService service = new ApiClientService(restTemplate, new HttpHeaders(), "http://localhost:8081");

        mockRestServiceServer.expect(requestTo("http://localhost:8081/api/variable/2/values"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"content\": [{ \"timestamp\": \"2019-01-14T16:47:44.51+01:00\",\"value\": 2,\"variableId\": 2,\"unit\": \"testUnit\"}]}",
                        MediaType.APPLICATION_JSON));

        List<ValueModel> result = service.getVariableValues(2);
        ValueModel model = result.get(0);
        Assert.assertEquals(model.getTimestamp().toString(), "Mon Jan 14 16:47:44 CET 2019");
        Assert.assertEquals((int)model.getValue(), 2);
        Assert.assertEquals((int)model.getVariableId(), 2);
        Assert.assertEquals(model.getUnit(), "testUnit");

        mockRestServiceServer.verify();
    }
}