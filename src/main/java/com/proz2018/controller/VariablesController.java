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
import com.proz2018.service.VariablesService;
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
    VariablesService variablesService;

    @GetMapping
    Page<Variable> allVariables(@RequestParam(name="ordering", defaultValue = "id") String ordering,
                                @RequestParam(name="pagesize", defaultValue = "20") Integer pageSize,
                                @RequestParam(name="page", defaultValue = "0") Integer page){

        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, ordering);
        return variablesService.getUserVariables(pageRequest);
    }

    @GetMapping("/{id}")
    Variable one(@PathVariable Integer id){
        return variablesService.getUserVariables(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Variable newVariable(@PathVariable Integer id, @RequestBody VariableModel variableModel){
        return variablesService.modifyVariable(id, variableModel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVariable(@PathVariable Integer id){
        variablesService.deleteVariable(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Value createValue(@PathVariable Integer id, @RequestParam(name = "value") Double value){
        return variablesService.createValue(id, value);
    }

    @GetMapping("/{id}/values")
    @ResponseStatus(HttpStatus.OK)
    public Page<Value> getAllValues(@PathVariable Integer id,
                                    @RequestParam(name="ordering", defaultValue = "timestamp") String ordering,
                                    @RequestParam(name="pagesize", defaultValue = "20") Integer pageSize,
                                    @RequestParam(name="page", defaultValue = "0") Integer page){

        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, ordering);
        return variablesService.getAllValues(id, pageRequest);
    }

    @DeleteMapping("/{id}/values")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllValues(@PathVariable Integer id,
                                @RequestParam(name="start") String start,
                                @RequestParam(name="end") String end){

        Timestamp startTime = Timestamp.valueOf(LocalDateTime.parse(start));
        Timestamp endTime = Timestamp.valueOf(LocalDateTime.parse(end));

        variablesService.deleteAllValues(id, startTime, endTime);
    }
}
