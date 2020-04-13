package com.springboot.dbtest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.springboot.dbtest.service.DbService;
 
@RestController
public class DbController {
    @Autowired
    DbService dbService;
     
    @RequestMapping("/")
    public @ResponseBody String root_test()throws Exception{  
        return "Hello World";
    }
 
    @RequestMapping("/dual")
    public @ResponseBody String dual()throws Exception{
        return dbService.getDual();
    }

    @RequestMapping("/now")
    public @ResponseBody String now()throws Exception{
        return dbService.getNow();
    }
}

