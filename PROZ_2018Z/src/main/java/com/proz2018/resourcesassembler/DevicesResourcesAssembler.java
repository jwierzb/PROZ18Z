package com.proz2018.resourcesassembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.proz2018.controller.DevicesController;
import com.proz2018.entities.Device;
import com.proz2018.entities.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;


@Component
public class DevicesResourcesAssembler implements ResourceAssembler<Device, Resource<Device>> {

    @Override
    public Resource<Device> toResource(Device device) {
        Resource<Device> rs= new Resource<Device>(
                device,
                linkTo(methodOn(DevicesController.class).one(device.getUser(), device.getId())).withRel("url"),
                linkTo(methodOn(DevicesController.class).variables(device.getUser(), device.getId())).withRel("variables")
                );
        return rs;
    }
}