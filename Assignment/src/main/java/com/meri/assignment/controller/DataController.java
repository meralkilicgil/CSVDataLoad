package com.meri.assignment.controller;

import com.meri.assignment.dto.DataDto;
import com.meri.assignment.service.DataService;
import com.meri.assignment.utils.ConvertUtils;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class DataController {

    private final DataService dataService;

    private ConvertUtils convertUtils = new ConvertUtils();

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String index(){
        return "hello";
    }

    //upload data with binary
    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestBody byte[] data) {
        try {
            File dataFile = ConvertUtils.convertBytesToFile(data);
            try(Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile)))){
                try (CSVReader csvReader = new CSVReader(reader)){
                    List<String[]> dataRows = csvReader.readAll();
                    List<DataDto> dataDtoList = convertUtils.convertCsvRowToDto(dataRows);
                    dataService.loadData(dataDtoList);
                    return new ResponseEntity<>("The data is loaded successfully", HttpStatusCode.valueOf(200));
                } catch (CsvException e) {
                    String errorMessage = e.getMessage();
                    return new ResponseEntity<>(errorMessage, HttpStatusCode.valueOf(404));
                }
            }
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadDataAsFile(@RequestParam("file") MultipartFile file) {
        try {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                try (CSVReader csvReader = new CSVReader(reader)) {
                    List<String[]> dataRows = csvReader.readAll();
                    List<DataDto> dataDtoList = convertUtils.convertCsvRowToDto(dataRows);
                    dataService.loadData(dataDtoList);
                    return new ResponseEntity<>("The data is loaded successfully", HttpStatusCode.valueOf(200));
                } catch (CsvException e) {
                    String errorMessage = e.getMessage();
                    return new ResponseEntity<>(errorMessage, HttpStatusCode.valueOf(404));
                }
            }
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatusCode.valueOf(404));
        }
    }

    // fetch all data
    @GetMapping("/getAll")
    public ResponseEntity<String> getAllData(HttpServletResponse response) {
        try {
            List<DataDto> dataDtoList = dataService.getAllData();
            if (dataDtoList != null && !dataDtoList.isEmpty()) {
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=\"allData.csv\"");
                ServletOutputStream outputStream = response.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                writer.println("\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"");
                dataDtoList.stream().forEach(dataDto -> writer.println(convertUtils.dataDtoToCSV(dataDto)));
                writer.close();
                return new ResponseEntity<>("The csv file is ready to download", HttpStatusCode.valueOf(200));
            }
            return new ResponseEntity<>("Any data record cannot be found.", HttpStatusCode.valueOf(404));
        }
        catch (Exception e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatusCode.valueOf(404));
        }
    }

    // fetch data by code
    @GetMapping("/getData")
    public ResponseEntity<Object> getDataByCode(@RequestParam("code") String code){
        try {
            DataDto data = dataService.getDataByCode(code);
            return ResponseEntity.ok(data);
        }
        catch (Exception e){
            System.out.println("Exception occurred during get data by code");
            return ResponseEntity.notFound().build();
        }
    }

    // delete all data
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllData(){
        try {
            dataService.deleteAllData();
            return new ResponseEntity<>("All data are deleted successfully", HttpStatusCode.valueOf(200));
        }
        catch (Exception e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatusCode.valueOf(404));
        }
    }
}
