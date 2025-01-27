package com.github.stazxr.zblog.util.office;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.stazxr.zblog.util.time.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Excel 工具类
 *
 * @author SunTao
 * @since 2022-04-12
 */
@Slf4j
public class ExcelUtils {
    public static final String XLS = "xls";

    public static final String XLSX = "xlsx";

    private static final int HIDE_COLUMN_WIDTH = 0;

    private static final int DEFAULT_COLUMN_WIDTH = 3766;

    /**
     * 是否是Excel文件
     *
     * @param filename 文件名
     * @return boolean
     */
    public static boolean isExcelFile(String filename) {
        return filename.endsWith(XLS) || filename.endsWith(XLSX);
    }

    /**
     * 单元格是否为空
     *
     * @param cell 单元格
     * @return boolean
     */
    public static boolean isCellBlank(Cell cell) {
        return cell == null || cell.getCellTypeEnum() == CellType.BLANK;
    }

    /**
     * 获取单元格内容，返回字符串
     *
     * @param cell 单元格
     * @return String
     */
    public static String getStringCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return DateUtils.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), "yyyy-MM-dd");
                } else {
                    // Bug待处理，整数会带小数点
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    /**
     * 导出Excel
     *
     * @param numFlag 表格是否带有序号
     * @param workbook excel
     * @param filename 文件名
     * @param sheetName 页签名
     * @param headers 表头
     * @param tableNames 表头对应的字段名
     * @param list 数据列表
     * @param hideCols 隐藏列
     * @param response 响应
     */
    public static void exportExcel(boolean numFlag, HSSFWorkbook workbook, String filename, String sheetName,
            String[] headers, String[] tableNames, List<?> list, Integer[] hideCols, HttpServletResponse response) {
        if (numFlag && headers.length - 1 != tableNames.length) {
            throw new RuntimeException("第一列为序号时，headers的长度比tableNames的长度大一，请核对代码");
        }

        if (!numFlag && headers.length != tableNames.length) {
            throw new RuntimeException("第一列不为序号时，headers的长度应与tableNames的长度一致，请核对代码");
        }

        // 创建sheet页
        HSSFSheet sheet;
        if (StringUtils.isEmpty(sheetName)) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(sheetName);
        }

        // 设置列宽
        List<Integer> hc2 = hideCols == null ? new ArrayList<>() : Arrays.asList(hideCols);
        for (int i = 0; i < headers.length; ++i) {
            sheet.setColumnWidth(i, hc2.contains(i) ? HIDE_COLUMN_WIDTH : DEFAULT_COLUMN_WIDTH);
        }

        // 创建表头
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(new HSSFRichTextString(headers[i]));
        }

        // 导入数据
        int rowNum = 1;
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            HSSFRow row1 = sheet.createRow(rowNum);
            if (numFlag) {
                for (int i = 0; i < headers.length; ++i) {
                    if (i == 0) {
                        row1.createCell(i).setCellValue(new HSSFRichTextString(rowNum + ""));
                    } else {
                        Object o = jsonObject.get(tableNames[i - 1]);
                        if (o == null) {
                            // CellType.BLANK = 3
                            row1.createCell(i).setCellValue(3);
                        } else {
                            String value = String.valueOf(o);
                            row1.createCell(i).setCellValue(value);
                        }
                    }
                }
            } else {
                for (int i = 0; i < headers.length; ++i) {
                    Object o = jsonObject.get(tableNames[i]);
                    if (o == null) {
                        row1.createCell(i).setCellValue("");
                    } else {
                        String value = String.valueOf(o);
                        row1.createCell(i).setCellValue(value);
                    }
                }
            }

            rowNum++;
        }

        try {
            String realFilename = filename.concat(".xls");
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realFilename, "UTF-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("export file catch eor", e);
            throw new RuntimeException("系统发生未知异常~");
        }
    }
}
