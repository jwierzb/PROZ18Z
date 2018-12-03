package com.proz2018;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.proz2018.controller.DevicesController;
import com.proz2018.entities.Device;
import com.proz2018.entities.User;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;


@Component
public class DevicesResourcesAssembler implements ResourceAssembler<Device, Resource<Device>> {

    @Override
    public Resource<Device> toResource(Device device) {

        return new Resource<>(device,
                linkTo(methodOn(DevicesController.class).one(device.getUser(), device.getId())).withRel("url"),
                linkTo(methodOn(DevicesController.class).all(device.getUser())).withRel("devices"));
    }
}