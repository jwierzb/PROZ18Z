package com.proz2018.service;

import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.dao.VariableDao;
import com.proz2018.entities.Device;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Variable;
import com.proz2018.exception.DeviceNotFoundException;
import com.proz2018.model.DeviceModel;
import com.proz2018.model.VariableModel;
import com.proz2018.resourcesassembler.DevicesResourcesAssembler;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private DeviceDao deviceDao;
    private DevicesResourcesAssembler assembler;
    private UserService userService;
    private VariableDao varsRepository;

    @Autowired
    public DeviceService(DeviceDao deviceDao, DevicesResourcesAssembler assembler, UserService userService, VariableDao varsRepository) {
        this.deviceDao = deviceDao;
        this.assembler = assembler;
        this.userService = userService;
        this.varsRepository = varsRepository;
    }

    public List<Resource<Device>> getAllUserDevices(PageRequest pageRequest) {
        UserEntity userEntity = userService.getCurrent();
        Page<Device> devices = deviceDao.findByUser(userEntity, pageRequest);
        return devices.stream().map(dv -> assembler.toResource(dv)).collect(Collectors.toList());
    }

    public Resource<Device> getDevice(Integer id) {
        UserEntity userEntity = userService.getCurrent();
        return assembler.toResource(deviceDao.findByUserAndId(userEntity, id).orElseThrow(() -> new DeviceNotFoundException(id.toString())));
    }

    public Resource<Device> newDevice(DeviceModel deviceModel) {
        UserEntity userEntity = userService.getCurrent();
        Device device = new Device(deviceModel.getName(),deviceModel.isEnabled(),deviceModel.getDescription(),deviceModel.getTags());
        device.setUser(userEntity);
        return assembler.toResource(deviceDao.saveAndFlush(device));
    }

    public Resource<Device> modifyDevice(Integer id, DeviceModel deviceModel) {
        UserEntity userEntity = userService.getCurrent();
        Device device = deviceDao.findByUserAndId(userEntity, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));

        if(!deviceModel.getName().isEmpty()) device.setDeviceName(deviceModel.getName());
        if(!deviceModel.getDescription().isEmpty()) device.setDescription(deviceModel.getDescription());
        if(!deviceModel.getTags().isEmpty()) device.setTags(deviceModel.getTags());
        device.setEnabled(deviceModel.isEnabled());
        return assembler.toResource(deviceDao.save(device));
    }

    public void deleteDevice(Integer id) {
        UserEntity userEntity = userService.getCurrent();
        Device device = deviceDao.findByUserAndId(userEntity, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));
        deviceDao.delete(device);
    }

    public Variable newDeviceVariable(Integer id, VariableModel variableModel) {
        UserEntity userEntity = userService.getCurrent();
        Device device = deviceDao.findByUserAndId(userEntity, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));
        Variable var = Variable.builder()
                .description(variableModel.getDescription())
                .name(variableModel.getName())
                .tags(variableModel.getTags())
                .unit(variableModel.getUnit())
                .lastValue(null)
                .device(device)
                .user(userEntity)
                .build();
        device.setVariablesCount(device.getVariablesCount()+1);
        return varsRepository.save(var);
    }

    public List<Variable> getDeviceVariables(Integer id) {
        UserEntity userEntity = userService.getCurrent();
        Device device = deviceDao.findByUserAndId(userEntity, id).orElseThrow(() -> new DeviceNotFoundException(id.toString()));
        return varsRepository.findAllByUserAndDevice(userEntity, device);
    }

    public void incrementVariablesCount(Device device) {
        device.setVariablesCount(device.getVariablesCount()+1);
        deviceDao.save(device);
    }

    public void decrementVariablesCount(Device device) {
        device.setVariablesCount(device.getVariablesCount()-1);
        deviceDao.save(device);
    }
}
