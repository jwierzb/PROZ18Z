package com.proz2018.controller;

import com.proz2018.Application;
import com.proz2018.config.EntityManagerConfig;
import com.proz2018.controller.DevicesController;
import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.dao.VariableDao;
import com.proz2018.entities.Device;
import com.proz2018.entities.User;
import com.proz2018.exception.DeviceNotFoundException;
import com.proz2018.exception.UserNotFoundException;
import com.proz2018.resourcesassembler.DevicesResourcesAssembler;
import com.proz2018.security.TokenAuthenticationFilter;
import com.proz2018.security.TokenAuthenticationProvider;
import com.proz2018.security.UserAuthenticationService;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DevicesController.class)
@Import({DevicesResourcesAssembler.class})
public class DevicesControllerTest {




    @MockBean
    private TokenAuthenticationProvider tAP;
    @MockBean
    private TokenAuthenticationFilter tAF;
    @MockBean
    private DeviceDao devDao;
    @MockBean
    private UserDao userDao;
    @MockBean
    private VariableDao varDao;

    @Autowired
    DevicesResourcesAssembler devicesResourcesAssembler;

    @MockBean
    UserAuthenticationService tokenService;

    @MockBean
    private User userExample;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void test() throws Exception {

        //when(tAF.attemptAuthentication(any(), any())).thenReturn(tAF.getAuthenticationManager().authenticate(any()));

        MvcResult result = this.mockMvc.perform(get("/api/devices/122/")
                .header("Authorization", "Token test"))
                .andExpect(status().isOk()).andReturn();


        System.out.println(result.getResponse().getContentAsString());
    }


}