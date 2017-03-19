package com.chenx.utils;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.Random;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
@Log4j
public class NameUtils {

    private static String [] 外姓 = new String []{"尼古拉斯", "杰森", "莱昂纳多", "克里斯",
    "安吉丽娜", "威尔", "布鲁斯", "汤姆", "金", "本杰明"};

    private static String [] 复姓 = new String []{"端木", "上官", "司马", "东方", "独孤",
            "南宫", "宇智波", "旋涡", "夏侯", "诸葛", "尉迟"};

    private static String [] 名 = new String []{"赵四", "小郭郭", "刘能", "日天", "小红", "小明",
    "二狗", "二仔", "布鲁斯", "三顺", "翠花", "丫蛋", "秀娥", "玉凤", "招弟"};

    public static String getFuckingName(){
        String name = "";
        if (new Random().nextInt(2) == 0){
            name = 外姓[new Random().nextInt(外姓.length)];
        }else {
            name = 复姓[new Random().nextInt(外姓.length)];
        }
        return name + 名[new Random().nextInt(名.length)];
    }

    @Test
    public void test (){
        log.info("------名字----" + NameUtils.getFuckingName());
    }
}
