package com.szzh.audio.newviewpager.indicator;

import java.util.regex.Pattern;

/**
 * Created by jzz
 * on 2017/4/4.
 *
 * desc:
 */

public final class StringUtils {


    public static boolean machPhoneNum(CharSequence phoneNumber) {

        String regex = "^[1][34578][0-9]\\d{8}$";
        // Pattern pattern = Pattern.compile(regex);
        // pattern.matcher(phoneNumber).matches();

        //第二种就是对一种的一种封装
        return Pattern.matches(regex, phoneNumber);
    }
}
