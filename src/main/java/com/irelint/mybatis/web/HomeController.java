package com.irelint.mybatis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhaoht on 2015-11-14.
 */
@Controller
@RequestMapping
public class HomeController {

    public String home(){
        return "hollo";
    }
}
