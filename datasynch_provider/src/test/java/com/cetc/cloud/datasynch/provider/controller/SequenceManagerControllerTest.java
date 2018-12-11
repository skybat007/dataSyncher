package com.cetc.cloud.datasynch.provider.controller;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/10 16:36
 * Updater:     by luolinjie
 * Update_Date: 2018/12/10
 * Update_Description: luolinjie 补充
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SequenceManagerControllerTest extends TestCase {

    @Autowired
    SequenceManagerController sequenceManagerController;

    @Test
    public void testGetAllSequenceNameList() throws Exception {
        List allSequenceNameList = sequenceManagerController.getAllSequenceNameList();
        System.out.println(allSequenceNameList);
    }

    @Test
    public void testExactSequenceByTbName() throws Exception {


        String tableName = "BJ2113";
        boolean b = sequenceManagerController.exactSequenceByTbName(tableName);


        System.out.println(b);
    }
    @Test
    public void testSort() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = SequenceManagerController.class.getClassLoader().getResourceAsStream("TableSeqNameMapping.properties");
        properties.load(resourceAsStream);
        Set<Object> keySet = properties.keySet();
        ArrayList<String> list1 = new ArrayList<String>();
        Iterator<Object> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            list1.add((String) next);
        }
        Object[] arr = list1.toArray();
        Arrays.sort(arr);

        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }

    @Test
    public void testExactAllSequences() throws Exception {


        String s = sequenceManagerController.exactAllSequences();
        System.out.println(s);
    }
}