package com.meri.assignment.dao;

import com.meri.assignment.entity.Data;
import com.meri.assignment.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataDao {

    private final DataRepository dataRepository;

    public DataDao(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<Data> getAll(){
        return dataRepository.findAll();
    }

    public Data getByCode(String code){
        return dataRepository.findByCode(code);
    }

    public void save(List<Data> dataList){
        dataRepository.saveAll(dataList);
    }

    public void deleteAll(){
        dataRepository.deleteAll();
    }
}
