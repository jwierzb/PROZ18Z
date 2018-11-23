package com.proz2018;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class DevicesRepository implements JpaRepository<Devices, String> {
    @Override
    public List<Devices> findAll() {
        return null;
    }

    @Override
    public List<Devices> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Devices> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Devices> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Devices devices) {

    }

    @Override
    public void deleteAll(Iterable<? extends Devices> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Devices> S save(S s) {
        return null;
    }

    @Override
    public <S extends Devices> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Devices> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Devices> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Devices> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Devices getOne(String s) {
        return null;
    }

    @Override
    public <S extends Devices> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Devices> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Devices> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Devices> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Devices> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Devices> boolean exists(Example<S> example) {
        return false;
    }
}
