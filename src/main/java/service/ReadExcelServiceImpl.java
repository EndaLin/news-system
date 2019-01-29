package service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wt
 * @Date: 2018/11/26 20:14
 */
public class ReadExcelServiceImpl {
    // 总行数
    private int totalRows = 0;
    // 总列数
    private int totalCells = 0;
    // 错误信息
    private String errorInfo;

    public ReadExcelServiceImpl() {
        super();
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {

        List<List<String>> dataLst = null;

        try {

            /** 根据Excel版本选择创建Workbook的方式 */

            Workbook wb = null;

            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return dataLst;

    }

    @SuppressWarnings("deprecation")
    public List<List<String>> read(Workbook wb) {

        List<List<String>> dataLst = new ArrayList<>();

        /** 得到第一个shell */

        Sheet sheet = wb.getSheetAt(0);

        /** 得到Excel的行数 */

        this.totalRows = sheet.getPhysicalNumberOfRows();

        /** 得到Excel的列数 */

        if (this.totalRows >= 1 && sheet.getRow(0) != null) {

            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();

        }

        /** 循环Excel的行 */

        for (int r = 0; r < this.totalRows; r++) {

            Row row = sheet.getRow(r);

            if (row == null) {

                continue;

            }

            List<String> rowLst = new ArrayList<>();

            /** 循环Excel的列 */

            for (int c = 0; c < this.getTotalCells(); c++) {

                Cell cell = row.getCell(c);

                String cellValue = "";

                if (cell != null) {
                    // 以下是判断数据的类型
                    switch (cell.getCellTypeEnum()) {
                        // 数字
                        case NUMERIC:
                            cellValue = String.valueOf(cell.getNumericCellValue());
                            break;
                        // 字符串
                        case STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        // Boolean
                        case BOOLEAN:
                            cellValue = String.valueOf(cell.getNumericCellValue());
                            break;
                        // 公式
                        case FORMULA:
                            cellValue = String.valueOf(cell.getCellFormula());
                            break;
                        // 空值
                        case BLANK:
                            cellValue = "";
                            break;
                        // 故障
                        case ERROR:
                            cellValue = "非法字符";
                            break;

                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }

                rowLst.add(cellValue);

            }

            /** 保存第r行的第c列 */

            dataLst.add(rowLst);

        }

        return dataLst;

    }

}
