package com.proz2018.controller;


import com.proz2018.dao.VariableDao;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Variable;
import com.proz2018.exception.DeviceNotFoundException;
import com.proz2018.model.DeviceModel;
import com.proz2018.model.VariableModel;
import com.proz2018.resourcesassembler.DevicesResourcesAssembler;
import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.entities.Device;
import com.proz2018.service.DeviceService;
import com.proz2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/devices")
public class DevicesController {

    private DeviceService deviceService;
    private UserService userService;

    @Autowired
    public DevicesController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    // Aggregate
    @GetMapping(produces = "application/json")
    public List<Resource<Device>>  all(@RequestParam(name="ordering", defaultValue = "createdAtDate") String ordering,
                                       @RequestParam(name="pagesize", defaultValue = "1000000") Integer pageSize,
                                       @RequestParam(name="page", defaultValue = "0") Integer page){
        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, ordering);
        return deviceService.getAllUserDevices(pageRequest);
    }

    // Single item
    @GetMapping("/{id}")
    public Resource<Device>  one(@PathVariable Integer id){
        return deviceService.getDevice(id);
    }



    // Single item
    @PostMapping
    public Resource<Device>  newDevice(@RequestBody DeviceModel deviceModel) throws URISyntaxException
    {
        return deviceService.newDevice(deviceModel);
    }

    //Modify device
    @PutMapping("/{id}")
    public Resource<Device> modifyDevice(@PathVariable Integer id,
                                         @RequestBody DeviceModel deviceModel) {
        return deviceService.modifyDevice(id, deviceModel);
    }


    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Integer id) {
        deviceService.deleteDevice(id);
    }

    @PostMapping("/{id}/new-variable")
    @ResponseStatus(HttpStatus.CREATED)
    public Variable newVariable(@PathVariable Integer id, @RequestBody VariableModel variableModel){
        return deviceService.newDeviceVariable(id, variableModel);
    }

    @GetMapping("/{id}/variables")
    @ResponseStatus(HttpStatus.OK)
    public List<Variable> deviceVariables(@PathVariable Integer id)
    {
        return deviceVariables(id);
    }

    DevicesController(){}
}
