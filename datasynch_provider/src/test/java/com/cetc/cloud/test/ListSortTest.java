package com.cetc.cloud.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
@Slf4j
public class ListSortTest {
    @Test
    public void sortList() {
        List list = new ArrayList<String>();
        list.add("BLK_SANXIAO_PLACE_COPY0");
        list.add("BLK_SANXIAO_PLACE_COPY4");
        list.add("BLK_SANXIAO_PLACE_COPY2");
        list.add("BLK_SANXIAO_PLACE_COPY5");

        for (int i = 0; i < list.size(); i++) {
            log.info("member" + i + ":" + list.get(i));
        }

        list.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String obj1 = (String) o1;
                String obj2 = (String) o2;
                if (obj1.length() > 2 && obj2.length() > 2) {
                    if (Integer.valueOf(obj1.substring(obj1.length() - 1, obj1.length())) > Integer.valueOf(obj2.substring(obj2.length() - 1, obj2.length()))) {
                        return 1;
                    } else if (Integer.valueOf(obj1.substring(obj1.length() - 1, obj1.length())) < Integer.valueOf(obj2.substring(obj2.length() - 1, obj2.length()))) {
                        {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
        log.info("\nsort");
        for (int i = 0; i < list.size(); i++) {
            log.info("member" + i + ":" + list.get(i));
        }

    }
}
