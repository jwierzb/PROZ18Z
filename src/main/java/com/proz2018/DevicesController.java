package com.proz2018;



import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class DevicesController {

    private final DevicesRepository repository;

    private final DevicesResourcesAssembler assembler;

    public DevicesController(DevicesRepository repository, DevicesResourcesAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate
    @GetMapping("/devices")
    List<Devices> all(){
        return repository.findAll();
    }
    @PostMapping("/devices")
    Devices newDevice(@RequestBody Devices newDevices) {
        return repository.save(newDevices);
    }
    // Single item
    @GetMapping("/devices/{id}/")
    Resource<Devices>  one(@PathVariable String id)
    {
        Devices  device = repository.findById(id).orElseThrow(() -> new DevicesNotFoundException(id));
        return assembler.toResource(device);
    }


}