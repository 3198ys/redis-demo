package com.ys.demoredis.config;

import org.springframework.session.web.http.DefaultCookieSerializer;

import javax.servlet.http.HttpServletRequest;

public class SpecialCookieSerializer extends DefaultCookieSerializer {
    private String cookiePath;


    private String getCookiePath(HttpServletRequest request) {
        if (this.cookiePath == null) {
            return "/";
        }
        return this.cookiePath;
    }

}
