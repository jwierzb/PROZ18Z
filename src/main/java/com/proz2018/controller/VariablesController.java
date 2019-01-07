package com.proz2018.controller;


import com.proz2018.dao.DeviceDao;
import com.proz2018.dao.UserDao;
import com.proz2018.dao.ValueDao;
import com.proz2018.dao.VariableDao;
import com.proz2018.entities.Device;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Value;
import com.proz2018.entities.Variable;
import com.proz2018.model.VariableModel;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/variable")
public class VariablesController {

    @Autowired
    UserDao userDao;
    @Autowired
    VariableDao variableDao;
    @Autowired
    ValueDao valueDao;
    @Autowired
    DeviceDao deviceDao;

    @GetMapping
    Page<Variable> allVariables(@RequestParam(name="ordering", defaultValue = "id") String ordering,
                                @RequestParam(name="pagesize", defaultValue = "20") Integer pageSize,
                                @RequestParam(name="page", defaultValue = "0") Integer page){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDao.findByUsername(((UserDetails) principal).getUsername());

        return variableDao.findAllByUser(user, new PageRequest(page, pageSize, Sort.Direction.DESC, ordering));
    }

    @GetMapping("/{id}")
    Variable one(@PathVariable Integer id){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDao.findByUsername(((UserDetails) principal).getUsername());

        return variableDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Variable newVariable(@PathVariable Integer id, @RequestBody VariableModel variableModel){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDao.findByUsername(((UserDetails) principal).getUsername());

        Variable variable = variableDao.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException());

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVariable(@PathVariable Integer id){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDao.findByUsername(((UserDetails) principal).getUsername());

        Variable variable = variableDao.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException());
        Device device = variable.getDevice();
        device.setVariablesCount(device.getVariablesCount()-1);

        deviceDao.save(device);

        variableDao.delete(variable);

    }


    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Value createValue(@PathVariable Integer id, @RequestParam(name = "value") Double value){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDao.findByUsername(((UserDetails) principal).getUsername());

        Variable variable = variableDao.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException());
        Value val = new Value();
        val.setValue(value);
        val.setVariable(variable);
        variable.setLastValue(value);
        variableDao.save(variable);
        return valueDao.save(val);
    }

    @GetMapping("/{id}/values")
    @ResponseStatus(HttpStatus.OK)
    public Page<Value> getAllValues(@PathVariable Integer id,
                                    @RequestParam(name="ordering", defaultValue = "timestamp") String ordering,
                                    @RequestParam(name="pagesize", defaultValue = "20") Integer pageSize,
                                    @RequestParam(name="page", defaultValue = "0") Integer page){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDao.findByUsername(((UserDetails) principal).getUsername());

        Variable variable = variableDao.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException());

        return valueDao.findAllByVariable(variable, new PageRequest(page, pageSize, Sort.Direction.DESC, ordering));
    }

    @DeleteMapping("/{id}/values")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllValues(@PathVariable Integer id,
                                @RequestParam(name="start") String start,
                                @RequestParam(name="end") String end){


            Timestamp startTime = Timestamp.valueOf(LocalDateTime.parse(start));
            Timestamp endTime = Timestamp.valueOf(LocalDateTime.parse(end));


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDao.findByUsername(((UserDetails) principal).getUsername());

        Variable variable = variableDao.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException());

        valueDao.deleteAllByTimestampBetweenAndVariable(startTime, endTime, variable);
    }
}
