package com.proz2018.controller;


import com.proz2018.dao.VariableDao;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Variable;
import com.proz2018.exception.DeviceNotFoundException;
import com.proz2018.model.DeviceModel;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ResponseBody
@RequestMapping("/api/devices")
@Data
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
        UserEntity user= ((UserEntity) principal);

        //TODO exception handling, wraping resource<device> pageable, sorting
        Page<Device> devices = deviceDao.findByUser(user, new PageRequest(page, pageSize, Sort.Direction.DESC, ordering));
        //List <Device> devices = deviceDao.findAllByUserId(user.getId());
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

        Device device = deviceDao.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));
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
    public Resource<Device>  newDevice(@RequestBody DeviceModel deviceModel) throws URISyntaxException
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = ((UserEntity) principal);


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
        UserEntity user = ((UserEntity) principal);


        Device device = deviceDao.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));


        if(deviceModel.getName().isEmpty()) deviceModel.setName(device.getDeviceName());
        if(deviceModel.getDescription().isEmpty()) deviceModel.setDescription(device.getDescription());
        if(deviceModel.getTags().isEmpty()) deviceModel.setTags(device.getTags());

        deviceDao.update(user.getId(), id, deviceModel.getDescription(), deviceModel.getTags(), deviceModel.isEnabled(), deviceModel.getName());
        /**
         * This entityManager call is run due to the problem jparepository would give cashed instance of device
         * except the new, modified one
         */
        entityManager.clear();

        return assembler.toResource(deviceDao.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString())));
    }
    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Integer id)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = ((UserEntity) principal);

        //find device
        Device device = deviceDao.findByUserIdAndId(user.getId(), id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));




    }

    DevicesController(){}
}
