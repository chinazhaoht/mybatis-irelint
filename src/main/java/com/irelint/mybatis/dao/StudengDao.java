package com.irelint.mybatis.dao;

import com.irelint.mybatis.model.Student;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaoht on 2016-01-24.
 */
public interface StudengDao {
    public Student select(Integer id);
}
