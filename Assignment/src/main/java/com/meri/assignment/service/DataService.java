package com.meri.assignment.service;

import com.meri.assignment.dao.DataDao;
import com.meri.assignment.dto.DataDto;
import com.meri.assignment.entity.Data;
import com.meri.assignment.utils.ConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DataService {

    private final DataDao dataDao;

    public DataService(DataDao dataDao) {
        this.dataDao = dataDao;
    }

    public List<DataDto> getAllData(){
        List<Data> dataList = dataDao.getAll();
        return dataList.stream().map(ConvertUtils::convertDataEntityToDataDto).toList();
    }

    public DataDto getDataByCode(String code){
        Data data = dataDao.getByCode(code);
        return ConvertUtils.convertDataEntityToDataDto(data);
    }

    public void loadData(List<DataDto> dataDtoList){
        List<Data> dataList = dataDtoList.stream().map(ConvertUtils::convertDataDtoToDataEntity).toList();
        dataDao.save(dataList);
    }

    public void deleteAllData(){
        dataDao.deleteAll();
    }
}
