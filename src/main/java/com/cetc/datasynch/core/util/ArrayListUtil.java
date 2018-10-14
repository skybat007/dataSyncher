package com.cetc.datasynch.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llj on 2018/10/14.
 */
public class ArrayListUtil {
    /**
     * 打印List的时候，不显示中括号
     * @return
     */
    public static String toStringWithoutBracket(List list){

        if (null!=list) {
            String a = list.toString();
            String b = a.substring(1);
            String c = b.substring(0,b.length()-1);
            return c;
        }else {
            return "error";
        }
    }

}
