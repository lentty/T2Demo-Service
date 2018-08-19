package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.CheckinDAO;
import com.successfactors.t2.service.CheckinService;
import com.successfactors.t2.utils.CheckinCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
public class CheckinServiceImpl implements CheckinService {

    @Autowired
    private CheckinDAO checkinDAO;

    @Override
    public String getCheckinCode() {
        Map<Integer, String> codeMap;
        if (CheckinCodeFactory.codeMap.isEmpty()) {
            System.out.println("get from db");
            codeMap = checkinDAO.getAllCodes();
        } else {
            System.out.println("get from cache");
            codeMap = CheckinCodeFactory.codeMap;
        }
        int codeSize = codeMap.size();
        Random random = new Random();
        Integer number = random.nextInt(codeSize) + 1;
        System.out.println(number);
        return codeMap.get(number);
    }
}
