package com.spring.kiddiecare.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ExcelReadUtil {
    public void readCellText(File file) {
        // 워크북 생성
        XSSFWorkbook wb = new XSSFWorkbook();   //하나의 xlsx파일이다

// 시트 생성
        Sheet sheet = wb.createSheet("시트명"); //하나의 excel sheet


// row(행) 순서 변수, cell(셀) 순서 변수
        int rowCount = 0;
        int cellCount = 0;

// row(행) 생성
        Row row = sheet.createRow(rowCount++);

// cell(셀) 생성
        Cell cell = row.createCell(cellCount++);    //cell이 현재 행(row)의 열이라고 생각하자.

// cell(셀) 값 입력
        cell.setCellValue('값');
    }
}
