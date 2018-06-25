package com.cfc.dao.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * @auther fangchen.chai ON 2018/1/24
 */
public class User {
    private String  name;
    private Integer age;
    private Date birthday;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
