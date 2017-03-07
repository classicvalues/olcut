package com.sun.labs.util.props;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Tests the extraction of {@link java.util.Map} objects from a {@link PropertySheet}.
 */

public class MapTest {
    public MapTest() { }

    @Test
    public void mapTest() throws IOException {
        URL cu = getClass().getResource("mapConfig.xml");
        ConfigurationManager cm = new ConfigurationManager(cu);
        MapConfigurable m = (MapConfigurable) cm.lookup("mapTest");
        Map<String,String> map = m.map;
        Assert.assertEquals("stuff",map.get("things"));
        Assert.assertEquals("quux",map.get("foo"));
        Assert.assertNull(map.get("bar"));
    }
}
