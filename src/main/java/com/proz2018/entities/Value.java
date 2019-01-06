package com.proz2018.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;


@Entity
@Table(name = "value")
@Data
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_id")
    @JsonIgnore
    private Integer id;

    @Column(name = "timestamp")
    @CreationTimestamp
    @JsonIgnore
    Timestamp timestamp;


    @Column(name = "value")
    @NotNull
    Double value;

    @JoinColumn(name = "variable_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Variable variable;


    @JsonGetter
    public Integer getVariableId(){ return variable.getId();}

    @JsonGetter
    public String getUnit(){return variable.getUnit();}

    @JsonGetter
    public ZonedDateTime getTimestamp(){return timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());}

    public Value(){}
}
