package com.proz2018.controller;

import com.proz2018.Application;
import com.proz2018.config.EntityManagerConfig;
import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.entities.Device;
import com.proz2018.entities.User;
import com.proz2018.exception.DeviceNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DevicesControllerTest {

    @Autowired
    private DevicesController devicesController;

    @Autowired
    private DeviceDao devRepository;

    @Autowired
    private UserDao userRepository;

    private User userExample;

    private MockMvc mockMvc;

    @Before
    public void cleanDatabaseAndMakeUserEntity() throws Exception
    {
        devRepository.deleteAll();
        assertTrue("Devices repository is not null", devRepository.findAll().isEmpty());
        userRepository.deleteByUserName("testUser");
        userRepository.save("testUser", "testPassword", "email@email.com", LocalDateTime.now());
        userExample = userRepository.findByUserName("testUser").orElseThrow(() -> new NoSuchFieldException("Problem with users repository"));

    }

    @Test
    public void userDevicesRepoShouldntContainsAnyDevices()
    {
        Page<Device> result = devicesController.all(userExample,"created_at", 1000, 0);
        assertTrue("Users devices array should be empty", result.getContent().isEmpty());

    }
    @Test
    public void userShouldHaveOneDevice() throws Exception
    {
        Resource<Device> dev = devicesController.newDevice(userExample, "Device1", "Opis", "true", "timer");
        assertNotNull("newDevice response shouldnt be null", dev.getContent());

        assertTrue("User shuld have one device", devicesController.all(userExample,"device_name", 1000, 0).getContent().size()==1);
    }
    @Test
    public void deviceShouldntBeCreated() throws Exception
    {
        Resource<Device> dev = null;
        try {
            dev = devicesController.newDevice(userExample, "Device1", "Opis", /* should be true*/"True", "timer");
        }catch(DeviceNotFoundException ex)
        {
            assertNotNull("Exception should not be null", ex);
        }
        assertNull("Device object should be null", dev);
    }

}