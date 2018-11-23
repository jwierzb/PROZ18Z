package com.proz2018;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class DevicesController {

    private final DevicesRepository repository;

    public DevicesController(DevicesRepository repository) {
        this.repository = repository;
    }

    // Aggregate
    @GetMapping("/devices")
    List<Devices> getAll(){
        return repository.findAll();
    }
    @PostMapping("/devices")
    Devices newDevice(@RequestBody Devices newDevices) {
        return repository.save(newDevices);
    }
    // Single item
    @GetMapping("/devices/{id}/")
    Devices  getOne(@PathVariable String id)
    {
        return repository.findById(id).orElseThrow(() -> new DevicesNotFoundException(id));
    }


}