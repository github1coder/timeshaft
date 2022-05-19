package com.timeshaft.after_end.service.userop;

import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.entity.UserToken;
import com.timeshaft.after_end.service.UserService;
import com.timeshaft.after_end.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserOp {
    @Autowired
    private UserService userService;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    String key = "userToken";


    public User register(String email, String password, String username, String checkCode) throws Exception {
        User u = new User(email, null, null, null, null);
        List<User> users = userService.queryAll(u);
        if (users.size() > 0) {
            throw new Exception("邮箱已被注册");
        }
        if (password.length()<6 || password.length()>16) {
            throw new Exception("密码长度应在6-16位之间");
        }
        checkCodeCheck(email, checkCode);

        String savePassword = myPasswordEncoder.encode(password);
        String baseURL = "http://182.92.163.68:8080/photo/" +
                (new Random().nextInt(12) + 1) + ".png";
        User user = new User(email, savePassword, username, baseURL, 0);
        userService.insert(user);
        return user;
    }

    public Map<String, Object> login(String email, String password) throws Exception {
        User u = new User(email, null, null, null, null);
        List<User> users = userService.queryAll(u);
        User user = users.get(0);
        boolean log = myPasswordEncoder.matches(password, user.getPassword());
        if (!log) {
            throw new Exception("密码错误");
        }
        String token = getRandomString(26);
        userTokenService.insert(new UserToken(user.getId(), token));
        Map<String, Object> userRes = new HashMap<>();
        userRes.put("id", user.getId());
        userRes.put("photo", user.getPhoto());
        userRes.put("username", user.getUsername());
        userRes.put("email", user.getEmail());
        userRes.put("ACCESS_TOKEN", token);

        ArrayList<String> tokens = new ArrayList<>();
        String userId = String.valueOf(user.getId());
        if (redisTemplate.hasKey(key)) {
            if (redisTemplate.opsForHash().hasKey(key, userId)) {
                tokens = (ArrayList<String>) redisTemplate.opsForHash().get(key, userId);
            }
        }
        tokens.add(token);
        redisTemplate.opsForHash().put(key, userId, tokens);

        return userRes;
    }

    public void changePwd(Integer user_id, String oldPassword, String newPassword, String checkCode) throws Exception {
        User user = userService.queryById(user_id);
        checkCodeCheck(user.getEmail(), checkCode);
        boolean flag = myPasswordEncoder.matches(oldPassword, user.getPassword());
        if (flag) {
            user.setPassword(myPasswordEncoder.encode(newPassword));
            userService.update(user);
        }
        else {
            throw new Exception("密码错误");
        }
    }

    public void logout(Integer user_id, String token) {
       List<UserToken> userTokens = userTokenService.queryAll(new UserToken(user_id, token));
       for (UserToken userToken: userTokens) {
           userTokenService.deleteById(userToken.getId());
       }

        String userId = String.valueOf(user_id);
       if (redisTemplate.hasKey(key)) {
           if (redisTemplate.opsForHash().hasKey(key, userId)) {
                ArrayList<String> tokens = (ArrayList<String>) redisTemplate.opsForHash().get(key, userId);
                if(tokens.contains(token)) {
                    tokens.remove(token);
                    redisTemplate.opsForHash().put(key, userId, tokens);
                }
           }
       }
    }

    private void checkCodeCheck(String email, String checkCode) throws Exception {
        if (redisTemplate.opsForValue().get(email)==null
                || !String.valueOf(redisTemplate.opsForValue().get(email)).equals(checkCode)) {
            throw new Exception("验证码错误");
        }
    }

    //length用户要求产生字符串的长度
    private String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
