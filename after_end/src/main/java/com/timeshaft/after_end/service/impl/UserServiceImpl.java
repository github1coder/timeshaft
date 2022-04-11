package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.service.UserService;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Override
    public boolean register() {
        return false;
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public boolean changePwd() {
        return false;
    }
}
