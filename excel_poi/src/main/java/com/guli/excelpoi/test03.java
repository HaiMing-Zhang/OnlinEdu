package com.guli.excelpoi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class test03 {
    @Test
    public void Test03() throws IOException {
        //创建新的工作簿
        Workbook hssfWorkbook = new HSSFWorkbook();
        //创建sheep
        Sheet sheet = hssfWorkbook.createSheet("工作表");
        //创建行
        Row row0 = sheet.createRow(0);
        //创建列
        Cell cell0_0 = row0.createCell(0);
        //给列赋值
        cell0_0.setCellValue("id");

        //创建列
        Cell cell0_1 = row0.createCell(1);
        //给列赋值
        cell0_1.setCellValue("name");

        //创建列
        Cell cell0_2 = row0.createCell(2);
        //给列赋值
        cell0_2.setCellValue("age");
        //生成文件流
        FileOutputStream outputStream =new FileOutputStream("d:/test-write03.xls");
        //存盘
        hssfWorkbook.write(outputStream);
        //关流
        outputStream.close();
    }
}
