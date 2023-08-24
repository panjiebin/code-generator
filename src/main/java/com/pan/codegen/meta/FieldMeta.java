package com.pan.codegen.meta;

import java.util.Objects;

/**
 * 成员变量元数据
 *
 * @author panjb
 */
public class FieldMeta {
    private String type;
    private String name;

    public FieldMeta(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldMeta fieldMeta = (FieldMeta) o;
        return name.equals(fieldMeta.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static FieldMeta of(String type, String name) {
        return new FieldMeta(type, name);
    }

}
