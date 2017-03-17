package com.chenx.utils;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class CalendarUtils {
    public static Calendar calendar = Calendar.getInstance();
    public static String getNameTail(){
        return "" + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE);
    }

    @Test
    public void test(){
        System.out.println(getNameTail());
    }
}
