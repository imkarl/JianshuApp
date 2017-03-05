package com.copy.jianshuapp.common;

import com.copy.jianshuapp.common.rom.RomType;

/**
 * ROM相关工具类
 * @version imkarl 2017-03
 */
public class RomUtils {
    private RomUtils() { }

    public static RomType getRomType() {
        return RomType.current();
    }

}
