package com.timeshaft.after_end.service.userop;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MyPasswordEncoder {
    public String encode(String rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.getBytes());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.getBytes()));
    }
}
