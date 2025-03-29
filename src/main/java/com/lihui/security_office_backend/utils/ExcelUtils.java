package com.lihui.security_office_backend.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

public class ExcelUtils {

    /**
     * 设置Excel下载响应头属性
     *
     * @param response    响应对象
     * @param rawFileName 文件名称
     */
    private static void setExcelRespProp(HttpServletResponse response, String rawFileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        try {
            String encodedFileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + ".xlsx\"; filename*=UTF-8''" + encodedFileName + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel文件
     *
     * @param response                响应对象
     * @param dataList                导出的数据集
     * @param fileName                文件名称
     * @param clazz                   数据类型
     * @param excludeColumnFieldNames 排除导出的字段
     */
    public static <T> void exportExcel(HttpServletResponse response, List<T> dataList, String fileName, Class<? extends T> clazz, Set<String> excludeColumnFieldNames) {
        setExcelRespProp(response, fileName);
        try (OutputStream outputStream = response.getOutputStream()) {
            EasyExcel.write(outputStream, clazz)
                    .excelType(ExcelTypeEnum.XLSX)
                    .excludeColumnFieldNames(excludeColumnFieldNames)
                    .sheet("Sheet1")
                    .doWrite(dataList);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
                response.getWriter().write("{\"message\":\"导出失败，请重试\"}");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 导入Excel文件
     *
     * @param file     上传的Excel文件
     * @param clazz    数据类型
     * @param listener Excel读取监听器
     */
    public static <T> void importExcel(MultipartFile file, Class<T> clazz, ReadListener<T> listener) {
        try {
            ExcelReaderBuilder readerBuilder = EasyExcel.read(file.getInputStream(), clazz, listener);
            readerBuilder.sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel文件读取失败");
        }
    }
}
