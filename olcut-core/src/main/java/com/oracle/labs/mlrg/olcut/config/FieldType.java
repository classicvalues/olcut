package com.oracle.labs.mlrg.olcut.config;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The types of fields that can be annotated by the {@link Config} annotation.
 */
public enum FieldType {

    //Primitives
    BOOLEAN(boolean.class, Boolean.class),
    BYTE(byte.class, Byte.class),
    SHORT(short.class, Short.class),
    INTEGER(int.class, Integer.class),
    LONG(long.class, Long.class),
    FLOAT(float.class, Float.class),
    DOUBLE(double.class, Double.class),
    STRING(String.class),
    //Primitive array types
    BYTE_ARRAY(byte[].class),
    SHORT_ARRAY(short[].class),
    INTEGER_ARRAY(int[].class),
    LONG_ARRAY(long[].class),
    FLOAT_ARRAY(float[].class),
    DOUBLE_ARRAY(double[].class),
    //Configurable classes
    CONFIGURABLE(Configurable.class),
    //Object array types
    STRING_ARRAY(String[].class),
    CONFIGURABLE_ARRAY(Configurable[].class),
    //Generic types - requires genericType argument to be set
    LIST(List.class),
    ENUM_SET(EnumSet.class),
    SET(Set.class),
    MAP(Map.class), //Map<String,T>
    //Misc types
    ATOMIC_INTEGER(AtomicInteger.class),
    ATOMIC_LONG(AtomicLong.class),
    FILE(File.class),
    PATH(Path.class),
    RANDOM(Random.class),
    ENUM(Enum.class);

    private static final Class<?> configurableClass = Configurable.class;
    private static final Class<?> configurableArrayClass = Configurable[].class;
    private static final Class<?> enumClass = Enum.class;

    private final Class<?>[] types;
    
    private final static Map<Class<?>,FieldType> m = new HashMap<>();
    
    public final static EnumSet<FieldType> arrayTypes = EnumSet.of(BYTE_ARRAY, SHORT_ARRAY,
                                                                  INTEGER_ARRAY, LONG_ARRAY,
                                                                  FLOAT_ARRAY, DOUBLE_ARRAY, STRING_ARRAY,
                                                                  CONFIGURABLE_ARRAY);

    public final static EnumSet<FieldType> listTypes = EnumSet.of(LIST,SET,ENUM_SET);

    public final static EnumSet<FieldType> simpleTypes = EnumSet.of(BOOLEAN, BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, STRING,
                                                                    CONFIGURABLE, ATOMIC_INTEGER,
                                                                    ATOMIC_LONG, FILE, PATH, RANDOM, ENUM);

    public final static EnumSet<FieldType> mapTypes = EnumSet.of(MAP);


    private FieldType(Class<?>... types) {
        this.types = types;
    }

    static {
        for (FieldType ft : FieldType.values()) {
            for (Class<?> type : ft.types) {
                m.put(type, ft);
            }
        }
    }

    public static FieldType getFieldType(Class<?> clazz) {
        if (configurableClass.isAssignableFrom(clazz)) {
            return m.get(configurableClass);
        } else if (configurableArrayClass.isAssignableFrom(clazz)) {
            return m.get(configurableArrayClass);
        } else if (enumClass.isAssignableFrom(clazz)) {
            return m.get(enumClass);
        } else {
            return m.get(clazz);
        }
    }

    public static FieldType getFieldType(Field f) {
        Class<?> fieldClass = f.getType();
        return getFieldType(fieldClass);
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public static boolean isBoolean(FieldType ft) {
        return ft.equals(FieldType.BOOLEAN);
    }
}