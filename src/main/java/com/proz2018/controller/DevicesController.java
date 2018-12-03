package com.proz2018.controller;


import com.proz2018.exception.DeviceNotFoundException;
import com.proz2018.DevicesResourcesAssembler;
import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.entities.Device;
import com.proz2018.entities.User;
import com.proz2018.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@ResponseBody
@RequestMapping("/api/devices")
public class DevicesController {

    private DeviceDao repository;
    private DevicesResourcesAssembler assembler;
    private UserDao userRepository;

    @Autowired
    DevicesController(DeviceDao repository, UserDao userRepository, DevicesResourcesAssembler assembler)
    {
        this.repository = repository;
        this.userRepository = userRepository;
        this.assembler = assembler;
    }


    // Aggregate
    @GetMapping
    public Resources<Resource<Device>>  all(@AuthenticationPrincipal final User user){
        List<Resource<Device>> device = repository.findByUserId(user.getId()).stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(device);
    }

    // Single item
    @GetMapping("/{id}")
    public Resource<Device>  one(@AuthenticationPrincipal final User user, @PathVariable Integer id)
    {
        Device device = repository.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));
        return assembler.toResource(device);
    }



    // Single item
    @PostMapping
    public Resource<Device>   newDevice(
            @AuthenticationPrincipal final User user,
            @RequestParam(value="name") String name, @RequestParam(value="description") String description,
            @RequestParam(value="enabled", defaultValue = "true") String enabled) throws URISyntaxException
    {
        boolean enabled_b;
        if(enabled.equals("false")) enabled_b = false;
        else if (enabled.equals("true")) enabled_b = true;
        else throw new DeviceNotFoundException("0");

        Device device = new Device(name,enabled_b,description);
        device.setUser(user);
        return assembler.toResource(repository.saveAndFlush(device));
    }
    DevicesController(){}
}
