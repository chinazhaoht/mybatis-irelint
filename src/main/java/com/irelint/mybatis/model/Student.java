package com.irelint.mybatis.model;

/**
 * Created by zhaoht on 2016-01-24.
 */
public class Student {

    static enum Sex{
        MAN,WOMAN
    }

    private Integer id;
    private String username;
    private String passwd;
    private Integer age;
    private Sex sex;


}