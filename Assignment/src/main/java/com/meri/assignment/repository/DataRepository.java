package com.meri.assignment.repository;

import com.meri.assignment.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, String> {

    List<Data> findAllByOrderByCode();

    Data findByCode(String code);
}
