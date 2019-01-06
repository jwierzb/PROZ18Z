package com.proz2018.controller;


import com.proz2018.dao.VariableDao;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Variable;
import com.proz2018.exception.DeviceNotFoundException;
import com.proz2018.resourcesassembler.DevicesResourcesAssembler;
import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.entities.Device;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ResponseBody
@RequestMapping("/api/devices")
@Data
public class DevicesController {

    @Autowired
    private DeviceDao repository;
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
        UserEntity user= ((UserEntity) principal);

        //TODO exception handling, wraping resource<device> pageable, sorting
        Page<Device> devices = repository.findByUser(user, new PageRequest(page, pageSize, Sort.Direction.DESC, ordering));
        //List <Device> devices = repository.findAllByUserId(user.getId());
        devices.forEach(device -> device.setNumber_of_variables(varsRepository.findByUserIdAndDeviceId(user.getId(), device.getId()).size()));
        //devices.forEach(assembler::toResource);
        List<Resource<Device>> device = devices.stream().map(dv -> assembler.toResource(dv)).collect(Collectors.toList());
        return  device;
    }

    // Single item
    @GetMapping("/{id}")
    public Resource<Device>  one(@PathVariable Integer id)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = ((UserEntity) principal);

        Device device = repository.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));
        device.setNumber_of_variables(varsRepository.findByUserIdAndDeviceId(user.getId(), id).size());

        return assembler.toResource(device);
    }

    // Single item
    @GetMapping("/{id}/variables")
    //TODO return Resourcese<List<>> except List
    public List<Variable> variables(@PathVariable Integer id)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = ((UserEntity) principal);

        List<Variable> vars = varsRepository.findByUserIdAndDeviceId(user.getId(), id);
        return vars;
    }

    // Single item
    @PostMapping
    public Resource<Device>   newDevice(
            @RequestParam(value="name") String name, @RequestParam(value="description", defaultValue = "") String description,
            @RequestParam(value="enabled", defaultValue = "true") String enabled,
            @RequestParam(value="tags", defaultValue = "") String tags) throws URISyntaxException
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = ((UserEntity) principal);



        boolean enabled_b;
        if(enabled.equals("false")) enabled_b = false;
        else if (enabled.equals("true")) enabled_b = true;
        else throw new DeviceNotFoundException("0"); // TODO exceptions
        Device device = new Device(name,enabled_b,description, tags);
        device.setUser(user);
        return assembler.toResource(repository.saveAndFlush(device));
    }

    //Modify device
    @PutMapping("/{id}")
    public Resource<Device> modifyDevice(@PathVariable Integer id,
                                         @RequestParam(value="name", required = false) String name,
                                         @RequestParam(value="description", required = false) String description,
                                         @RequestParam(value="enabled", required = false) String enabled,
                                         @RequestParam(value="tags", required = false) String tags)
    {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = ((UserEntity) principal);


        Device device = repository.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));

        Boolean enabled_b;
        if (enabled == null ) enabled_b = device.getEnabled();
        else if(enabled.equals("false")) enabled_b = false;
        else if (enabled.equals("true")) enabled_b = true;
        else throw new DeviceNotFoundException("0"); // TODO exceptions

        if(name == null) name = device.getDeviceName();
        if(description == null) description = device.getDescription();
        if(tags == null) tags = device.getTags();

        repository.update(user.getId(), id, description, tags, enabled_b, name);
        /**
         * This entityManager call is run due to the problem jparepository would give cashed instance of device
         * except the new, modified one
         */
        entityManager.clear();


        return assembler.toResource(repository.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString())));
    }


    DevicesController(){}
}
