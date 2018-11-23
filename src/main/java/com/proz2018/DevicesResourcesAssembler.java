package com.proz2018;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

        import org.springframework.hateoas.Resource;
        import org.springframework.hateoas.ResourceAssembler;
        import org.springframework.stereotype.Component;

@Component
class DevicesResourcesAssembler implements ResourceAssembler<Devices, Resource<Devices>> {

    @Override
    public Resource<Devices> toResource(Devices device) {

        return new Resource<>(device,
                linkTo(methodOn(DevicesController.class).one(device.getId())).withSelfRel(),
                linkTo(methodOn(DevicesController.class).all()).withRel("employees"));
    }
}