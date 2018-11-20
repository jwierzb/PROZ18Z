package com.proz2018;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class DevicesController {

    private final List<Devices> devices = new ArrayList<Devices>();


    @PostMapping("/devices")
    public Devices device(@RequestParam(value = "name") String name, @RequestParam(value = "label") String label, @RequestParam(value = "enabled", defaultValue = "true") String enabled) {
        Devices device = new Devices(label, name, enabled.equals("false") ? false : true, "asdf", new Date());
        devices.add(device);
        return device;
    }

    @GetMapping("/devices")
    List<Devices> getAll(){
        return devices;
    }
}

