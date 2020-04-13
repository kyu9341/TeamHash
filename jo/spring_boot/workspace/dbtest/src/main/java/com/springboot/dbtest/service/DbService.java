package com.springboot.dbtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.springboot.dbtest.dao.DbMapper;
 
@Service
public class DbService {
 
    @Autowired
    DbMapper dbMapper;
 
    /* select dual */
    public String getDual()throws Exception{
        return dbMapper.getDual();
    }

    public String getNow()throws Exception{
        return dbMapper.getNow();
    }
 
}

