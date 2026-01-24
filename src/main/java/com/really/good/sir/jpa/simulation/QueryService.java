package com.really.good.sir.jpa.simulation;

import javax.persistence.Column;
import java.lang.reflect.Field;

public class QueryService {
    public static String convertToSQL(Field f) {
        Class<?> t = f.getType();

//        if (t == String.class) return "VARCHAR(255)";
        if (t == Long.class || t == long.class) return "BIGINT";
        if (t == Integer.class || t == int.class) return "INT";
        if (t == Boolean.class || t == boolean.class) return "BOOLEAN";

        throw new RuntimeException("Unsupported field type: " + t.getName());
    }
    public static String getColumnType(Field f, Column c) {
        if (f.getType() == String.class) {
            return "VARCHAR(" + c.length() + ")";
        }
        return convertToSQL(f);
    }
}
