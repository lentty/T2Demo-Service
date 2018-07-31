package com.successfactors.t2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static void main(String... args){
        String dateStr = formatDate();
        System.out.println(dateStr);
    }
}
