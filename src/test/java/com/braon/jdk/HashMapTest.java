 package com.braon.jdk;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Jason on 2016/11/21.
 */
public class HashMapTest {
    private HashMap<String, String> map = new HashMap<String, String>();

    @Test
    public void put() throws Exception {
        for (int i = 0; i < 50 * 10000; ++i)
            map.put("test" + i, "test" + i);
    }

    @Test
    public void get() throws Exception {
        map.clear();
        map.put("test123456", "test123456");
        assertEquals("test123456", map.get("test123456"));
    }

    @Test
    public void getSize() throws Exception {
        map.clear();
        for (int i = 0; i < 1000; ++i) {
            map.put("test123456" + i, "test123456" + i);
            assertEquals("test123456" + i, map.get("test123456" + i));
        }
        assertEquals(1000, map.getSize());
        for (int i = 0; i < 1000; ++i)
                 map.remove("test123456" + i);
        assertEquals(0, map.getSize());
    }

    @Test
    public void getCapacity() throws Exception {
        map.clear();
        for (int i = 0; i < 1000; ++i) {
            map.put(i + "", i+ "");
        }
        assertEquals(2048, map.getCapacity());
    }

    @Test
    public void remove() throws Exception {
        map.put("123", "123");
        assertEquals("123", map.get("123"));
    }

}