package com.proz2018.dao;

import com.proz2018.entities.Value;
import com.proz2018.entities.Variable;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ValueDao extends JpaRepository<Value, Integer> {

    Page<Value> findAllByVariable(Variable variable, Pageable pageable);

    @Transactional
    void deleteAllByTimestampBetweenAndVariable(Timestamp left, Timestamp right, Variable variable);
}
