package cn.hj.blog.service;

import cn.hj.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
