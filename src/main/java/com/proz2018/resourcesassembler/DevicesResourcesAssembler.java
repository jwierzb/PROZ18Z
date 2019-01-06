package com.proz2018.resourcesassembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.proz2018.controller.DevicesController;
import com.proz2018.entities.Device;
import com.proz2018.entities.UserEntity;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;


@Component
public class DevicesResourcesAssembler implements ResourceAssembler<Device, Resource<Device>> {

    @Override
    public Resource<Device> toResource(Device device) {

        try {

            Resource<Device> rs= new Resource<Device>(
                    device,
                    linkTo(methodOn(DevicesController.class).one(device.getId())).withRel("url"),
                    linkTo(methodOn(DevicesController.class).deviceVariables(device.getId())).withRel("variables")
            );
            return rs;
        } catch (ClassCastException ex){
            throw ex;
        }


    }


}