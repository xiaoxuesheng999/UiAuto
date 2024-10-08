package com.xiaohe.util;

import lombok.Data;
import org.openqa.selenium.Cookie;

import java.util.Date;

@Data
public class CookieFromJson {
    private String name;
    private String value;
    private String domain;
    private String path;
    private String sameSite;
    private boolean isSecure;
    private boolean isHttpOnly;
    private Date expiry;
    public Cookie toSeleniumCookie() {
        return new Cookie(name,value,domain,path,expiry,isSecure, isHttpOnly,sameSite);
    }
}
