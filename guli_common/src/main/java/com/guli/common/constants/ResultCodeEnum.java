package com.guli.common.constants;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(true, 20000,"成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),
    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21003, "参数不正确"),
    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    FILE_TYPE_ERROR(false,21005,"文件格式错误"),
    FILE_CONTEXT_ERROR(false,21006,"文件内容有误"),
    FETCH_PLAYAUTH_ERROR(false,21007,"阿里云视频凭证获取失败"),
    EXCEL_DATA_IMPORT_ERROR(false, 21008, "Excel数据导入错误");

    private Boolean success;

    private Integer code;

    private String message;

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

}
