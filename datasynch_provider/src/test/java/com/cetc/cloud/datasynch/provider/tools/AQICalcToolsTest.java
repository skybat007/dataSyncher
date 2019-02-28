package com.cetc.cloud.datasynch.provider.tools;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/27.
 */
public class AQICalcToolsTest extends TestCase {

    public void testGetFinalAQIVal() throws Exception {
        ArrayList<List> objects = new ArrayList<>();
        List single = new ArrayList<String>();
        single.set(0,"a21031");
        single.set(1,35910);
        AQICalcTools.getFinalAQIVal(single);
    }
}