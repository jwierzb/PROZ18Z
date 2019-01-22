package com.proz2018.service;

import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.dao.ValueDao;
import com.proz2018.dao.VariableDao;
import com.proz2018.entities.Device;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Value;
import com.proz2018.entities.Variable;
import com.proz2018.model.VariableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

@Service
public class VariablesService {

    UserService userService;
    VariableDao variableDao;
    ValueDao valueDao;
    DeviceService deviceService;

    @Autowired
    public VariablesService(UserService userService, VariableDao variableDao, ValueDao valueDao, DeviceService deviceService) {
        this.userService = userService;
        this.variableDao = variableDao;
        this.valueDao = valueDao;
        this.deviceService = deviceService;
    }

    public Page<Variable> getUserVariables(PageRequest pageRequest) {
        UserEntity userEntity = userService.getCurrent();
        return variableDao.findAllByUser(userEntity, pageRequest);
    }

    public Variable getUserVariables(Integer id) {
        UserEntity userEntity = userService.getCurrent();
        return variableDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Variable modifyVariable(Integer id, VariableModel variableModel) {
        UserEntity userEntity = userService.getCurrent();
        Variable variable = variableDao.findByIdAndUser(id, userEntity).orElseThrow(() -> new EntityNotFoundException());

        if(variableModel.getDescription().isEmpty()) variableModel.setDescription(variable.getDescription());
        if(variableModel.getName().isEmpty()) variableModel.setName(variable.getName());
        if(variableModel.getTags().isEmpty()) variableModel.setTags(variable.getTags());
        if(variableModel.getUnit().isEmpty()) variableModel.setUnit(variable.getUnit());

        variable.setDescription(variableModel.getDescription());
        variable.setTags(variableModel.getTags());
        variable.setUnit(variableModel.getUnit());
        variable.setName(variableModel.getName());

        return variableDao.save(variable);
    }

    public void deleteVariable(Integer id) {
        UserEntity userEntity = userService.getCurrent();
        Variable variable = variableDao.findByIdAndUser(id, userEntity).orElseThrow(() -> new EntityNotFoundException());
        deviceService.decrementVariablesCount(variable.getDevice());
        variableDao.delete(variable);
    }

    public Value createValue(Integer id, Double value) {
        UserEntity userEntity = userService.getCurrent();
        Variable variable = variableDao.findByIdAndUser(id, userEntity).orElseThrow(() -> new EntityNotFoundException());
        Value val = new Value();
        val.setValue(value);
        val.setVariable(variable);
        variable.setLastValue(value);
        variableDao.save(variable);
        return valueDao.save(val);
    }

    public Page<Value> getAllValues(Integer id, PageRequest pageRequest) {
        UserEntity userEntity = userService.getCurrent();
        Variable variable = variableDao.findByIdAndUser(id, userEntity).orElseThrow(() -> new EntityNotFoundException());
        return valueDao.findAllByVariable(variable, pageRequest);
    }

    public void deleteAllValues(Integer id, Timestamp startTime, Timestamp endTime) {
        UserEntity userEntity = userService.getCurrent();
        Variable variable = variableDao.findByIdAndUser(id, userEntity).orElseThrow(() -> new EntityNotFoundException());
        valueDao.deleteAllByTimestampBetweenAndVariable(startTime, endTime, variable);
    }

}
