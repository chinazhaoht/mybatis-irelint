package com.irelint.mybatis.web;

import com.irelint.mybatis.model.Student;
import com.irelint.mybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @ResponseBody
    public Student select(@RequestParam Integer id){
        return studentService.findById(id);
    }
}
