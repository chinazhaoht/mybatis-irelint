package com.irelint.mybatis.service;

import com.irelint.mybatis.dao.StudengDao;
import com.irelint.mybatis.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by zhaoht on 2016-01-24.
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudengDao studengDao;

    public Student findById(Integer  id){
        return studengDao.select(id) ;
    }
}
