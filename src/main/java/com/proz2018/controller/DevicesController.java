package com.proz2018.controller;


import com.proz2018.DevicesNotFoundException;
import com.proz2018.DevicesResourcesAssembler;
import com.proz2018.dao.DeviceDao;
import com.proz2018.entities.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@ResponseBody
public class DevicesController {

    private final DeviceDao repository;
    private final DevicesResourcesAssembler assembler;

    @Autowired
    public DevicesController(DeviceDao repository, DevicesResourcesAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate
    @GetMapping("/devices")
    public Resources<Resource<Device>>  all(){
        List<Resource<Device>> device = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(device,
                linkTo(methodOn(DevicesController.class).all()).withSelfRel());
    }


    // Single item
    @GetMapping("/devices/{id}")
    public Resource<Device>  one(@PathVariable Integer id)
    {
        Device device = repository.findById(id).orElseThrow(() -> new DevicesNotFoundException(id.toString()));
        return assembler.toResource(device);
    }



    // Single item
    @PostMapping("/devices")
    public Resource<Device>   newDevice(@RequestParam(value="name") String name, @RequestParam(value="description") String description,
                                   @RequestParam(value="enabled", defaultValue = "true") String enabled) throws URISyntaxException
    {
        boolean enabled_b;
        if(enabled.equals("false")) enabled_b = false;
        else if (enabled.equals("true")) enabled_b = true;
        else throw new DevicesNotFoundException("0");

        Device device = new Device(name,enabled_b,description, 1);

        return assembler.toResource(repository.saveAndFlush(device));
    }

}
