package com.lihui.security_office_backend.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

public class ExportUtils {
    /**
     * 设置 excel 下载响应头属性
     *
     * @param response
     * @param rawFileName
     */
    public static void setExcelRespProp(HttpServletResponse response, String rawFileName) {
        // 设置响应内容类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 编码
        response.setCharacterEncoding("utf-8");
        // 设置文件名
        try {
            String encodedFileName = URLEncoder.encode(rawFileName + ".xlsx", "UTF-8").replaceAll("\\+", "%20");
            // 同时设置 filename 和 filename* 以兼容不同浏览器
            response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName + "; filename*=utf-8''" + encodedFileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出 Excel 文件
     *
     * @param response
     * @param dataList                要导出的数据集
     * @param fileName                文件名称
     * @param clazz                   导出的实体类
     * @param excludeColumnFieldNames 导出实体类中被排除的字段
     */
    public static <T> void downloadExportExcel(HttpServletResponse response, List<T> dataList, String fileName, Class<? extends T> clazz, Set<String> excludeColumnFieldNames) {
        setExcelRespProp(response, fileName);
        try {
            EasyExcel.write(response.getOutputStream())
                    .head(clazz)
                    .excludeColumnFieldNames(excludeColumnFieldNames)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(fileName)
                    .doWrite(dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}