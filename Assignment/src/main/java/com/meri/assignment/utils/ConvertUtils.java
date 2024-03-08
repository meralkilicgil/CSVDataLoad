package com.meri.assignment.utils;

import com.meri.assignment.dto.DataDto;
import com.meri.assignment.entity.Data;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertUtils {

    public static DataDto convertDataEntityToDataDto(Data dataEntity){
        DataDto dataDto = new DataDto();
        BeanUtils.copyProperties(dataEntity, dataDto);
        return dataDto;
    }

    public static Data convertDataDtoToDataEntity(DataDto dataDto){
        Data dataEntity = new Data();
        BeanUtils.copyProperties(dataDto, dataEntity);
        return dataEntity;
    }

    public static File convertBytesToFile(byte[] bytes) throws IOException {
        File file = new File("data.csv");
        try(FileOutputStream fos = new FileOutputStream(file)){
            fos.write(bytes);
        }
        return file;
    }

    public List<DataDto> convertCsvRowToDto(List<String[]> dataRows){
        List<DataDto> dataDtoList = new ArrayList<>();
        dataRows.remove(0); // remove header row
        dataDtoList = dataRows.stream().map(this::convertRowToDataDto).toList();
        return dataDtoList;
    }

    public DataDto convertRowToDataDto(String[] data){
        DataDto dataDto = new DataDto();
        dataDto.setSource(data[0]);
        dataDto.setCodeListCode(data[1]);
        dataDto.setCode(data[2]);
        dataDto.setDisplayValue(data[3]);
        dataDto.setLongDescription(data[4]);
        dataDto.setFromDate(data[5]);
        dataDto.setToDate(data[6]);
        dataDto.setSortingPriority(data[7]);
        return dataDto;
    }



    public String dataDtoToCSV(DataDto dataDto){
        return String.join(",",
                "\"" + dataDto.getSource() + "\"" ,
                "\"" + dataDto.getCodeListCode() + "\"" ,
                "\"" + dataDto.getCode() + "\"" ,
                "\"" + dataDto.getDisplayValue() + "\"" ,
                "\"" + dataDto.getLongDescription() + "\"" ,
                "\"" + dataDto.getFromDate() + "\"" ,
                "\"" + dataDto.getToDate() + "\"" ,
                "\"" + dataDto.getSortingPriority() + "\"" );
    }
}
