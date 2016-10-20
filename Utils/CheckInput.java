package com.kim.imageloader.Utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KIM on 2015/9/6 0006.
 */
public class CheckInput {

    /**
     * 校验手机号码是否是11位数字
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum){
        String telRegex = "[1][3578]\\d{9}";
        String exp = "[0-9]{11}";
        Pattern p = Pattern.compile(telRegex);
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 校验输入是否为姓名
     * @param name
     * @return
     */
    public static boolean isName(String name){
        //匹配英文 汉字 . 空格
        String exp = "[a-zA-Z\\u4E00-\\u9FA5\\s.]{1,}";
        Pattern p = Pattern.compile(exp);
        Matcher m = p.matcher(name);
        return m.matches();
    }




    /**
     * 判断图片路径是否http开头
     */

    public static String getPath(String path){
        if(!TextUtils.isEmpty(path)){
            if( !path.contains("http")){
//                path = App.rootUrl +path;
            }
            return path;
        }
        return null;
    }

}
