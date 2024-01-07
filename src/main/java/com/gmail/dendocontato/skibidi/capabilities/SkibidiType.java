package com.gmail.dendocontato.skibidi.capabilities;

import net.minecraft.util.StringRepresentable;

public enum SkibidiType implements StringRepresentable {
    NONE("none"),
    TOILET("toilet"),
    CAMERA("camera");

    private final String name;

    private SkibidiType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getName() {
        return this.name;
     }

    public static SkibidiType byName(String name) {
        for (SkibidiType value : SkibidiType.values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return SkibidiType.NONE;
    }
}
