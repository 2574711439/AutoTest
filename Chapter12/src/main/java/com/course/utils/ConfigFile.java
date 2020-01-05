package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    /**
     * 拼接测试地址
     * @param name
     * @return testUrl
     */
    public static String getUrl(InterfaceName name){
        String address = bundle.getString("test.url");
        String uri = "";
        //最终的测试地址
        String testUrl = "";

        if (name.equals(InterfaceName.ADDUSERINFO)){
            uri = bundle.getString("addUser.uri");
        }else if (name.equals(InterfaceName.GETUSERINFO)){
            uri = bundle.getString("getUserInfo.uri");
        }else if(name.equals(InterfaceName.GETUSERLIST)){
            uri = bundle.getString("getUserListInfo.uri");
        }else if(name.equals(InterfaceName.UPDATEUSERINFO)){
            uri = bundle.getString("updateUserInfo.uri");
        }else if(name.equals(InterfaceName.LOGIN)){
            uri = bundle.getString("login.uri");
        }
        testUrl = address + uri ;
        return testUrl;
    }
}
