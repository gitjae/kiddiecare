package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.excel.ExcelData;
import com.spring.kiddiecare.domain.excel.ExcelDataRepository;
import com.spring.kiddiecare.util.ExcelReadUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("data/parsing")
public class ExcelFileController {

    private final ExcelDataRepository excelDataRepository;

    @PostMapping(value ="excel", consumes = {"multipart/form-data"})
    public Map excelFileParsing(@RequestBody MultipartFile file, Model model) throws IOException {
        JSONObject jsonObject = new JSONObject();

        List<ExcelData> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
            Row row = worksheet.getRow(i);
            ExcelData data = new ExcelData();
            data.setNo(row.getCell(2).getStringCellValue());
            data.setSubject(row.getCell(3).getStringCellValue());

            Optional<ExcelData> databaseData = Optional.ofNullable(excelDataRepository.findBySubject(data.getSubject()));

            if(databaseData.isEmpty()){
                excelDataRepository.save(data);
            }
            dataList.add(data);
        }
        jsonObject.put("response","success");
        jsonObject.put("data",dataList);// 5

        return jsonObject.toMap();
    }

}
