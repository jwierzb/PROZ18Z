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

    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private DevicesResourcesAssembler assembler;
    @Autowired
    private UserDao userRepository;
    @Autowired
    private VariableDao varsRepository;
    @Autowired
    private EntityManager entityManager;



    // Aggregate
    @GetMapping(produces = "application/json")
    public List<Resource<Device>>  all(
                                            @RequestParam(name="ordering", defaultValue = "createdAtDate") String ordering,
                                            @RequestParam(name="pagesize", defaultValue = "1000000") Integer pageSize,
                                            @RequestParam(name="page", defaultValue = "0") Integer page){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(((UserDetails) principal).getUsername());


        //TODO exception handling, wraping resource<device> pageable, sorting
        Page<Device> devices = deviceDao.findByUser(user, new PageRequest(page, pageSize, Sort.Direction.DESC, ordering));
        //List <Device> devices = deviceDao.findAllByUserId(user.getId());

        //devices.forEach(assembler::toResource);
        List<Resource<Device>> device = devices.stream().map(dv -> assembler.toResource(dv)).collect(Collectors.toList());
        return  device;
    }

    // Single item
    @GetMapping("/{id}")
    public Resource<Device>  one(@PathVariable Integer id)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(((UserDetails) principal).getUsername());

        Device device = deviceDao.findByUserAndId(user, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));

        return assembler.toResource(device);
    }



    // Single item
    @PostMapping
    public Resource<Device>  newDevice(@RequestBody DeviceModel deviceModel) throws URISyntaxException
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(((UserDetails) principal).getUsername());

        Device device = new Device(deviceModel.getName(),deviceModel.isEnabled(),deviceModel.getDescription(),deviceModel.getTags());
        device.setUser(user);
        return assembler.toResource(deviceDao.saveAndFlush(device));
    }

    //Modify device
    @PutMapping("/{id}")
    public Resource<Device> modifyDevice(@PathVariable Integer id,
                                         @RequestBody DeviceModel deviceModel)
    {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(((UserDetails) principal).getUsername());

        Device device = deviceDao.findByUserAndId(user, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));


        if(!deviceModel.getName().isEmpty()) device.setDeviceName(deviceModel.getName());
        if(!deviceModel.getDescription().isEmpty()) device.setDescription(deviceModel.getDescription());
        if(!deviceModel.getTags().isEmpty()) device.setTags(deviceModel.getTags());
        device.setEnabled(deviceModel.isEnabled());

        return assembler.toResource(deviceDao.save(device));
    }


    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Integer id)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(((UserDetails) principal).getUsername());

        //find device
        Device device = deviceDao.findByUserAndId(user, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));

        deviceDao.delete(device);


    }

    @PostMapping("/{id}/new-variable")
    @ResponseStatus(HttpStatus.CREATED)
    public Variable newVariable(@PathVariable Integer id, @RequestBody VariableModel variableModel){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(((UserDetails) principal).getUsername());


        //find device
        Device device = deviceDao.findByUserAndId(user, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));

        Variable var = new Variable();
        var = var.builder()
                .description(variableModel.getDescription())
                .name(variableModel.getName())
                .tags(variableModel.getTags())
                .unit(variableModel.getUnit())
                .lastValue(null)
                .device(device)
                .user(user)
                .build();
        device.setVariablesCount(device.getVariablesCount()+1);
        deviceDao.save(device);
        return varsRepository.save(var);
    }

    @GetMapping("/{id}/variables")
    @ResponseStatus(HttpStatus.OK)
    public List<Variable> deviceVariables(@PathVariable Integer id)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(((UserDetails) principal).getUsername());
        //find device
        Device device = deviceDao.findByUserAndId(user, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));
        return varsRepository.findAllByUserAndDevice(user, device);
    }
    DevicesController(){}
}
