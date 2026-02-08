package com.bookmarket.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserContext {

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Map<String,Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        return userInfo.get("username").toString();
    }

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Map<String,Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        return (Long) userInfo.get("userId");
    }


}