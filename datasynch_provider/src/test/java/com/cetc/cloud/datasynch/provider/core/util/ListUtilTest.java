package com.cetc.cloud.datasynch.provider.core.util;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by Administrator on 2018/12/28.
 */
public class ListUtilTest extends TestCase {
    @Test
    public void testToStringNoBracketWithSingleQuot() throws Exception {
        HashSet<String> strings = new HashSet<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        strings.add("1");

        String s = ListUtil.toStringNoBracketWithSingleQuot(strings);
        System.out.println(s);
    }
}