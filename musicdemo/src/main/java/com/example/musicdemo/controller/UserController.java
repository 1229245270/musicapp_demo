package com.example.musicdemo.controller;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.Song;
import com.example.musicdemo.entity.SongList;
import com.example.musicdemo.entity.User;
import com.example.musicdemo.enums.ResultEnum;
import com.example.musicdemo.interceptor.Constant;
import com.example.musicdemo.service.UserService;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user/login")
    public Result<Object> login(@Valid User user, HttpServletRequest request, HttpServletResponse response){
        int code = userService.checkLogin(user);
        if(code == 200){
            request.getSession().setAttribute(Constant.CURRENT_USER,user);
            return ResultUtil.success(user);
        }else if(code == ResultEnum.LOGIN_ERROR_NULL.getCode()){
            return ResultUtil.error(ResultEnum.LOGIN_ERROR_NULL.getCode(),ResultEnum.LOGIN_ERROR_NULL.getMsg());
        }
        return ResultUtil.error(ResultEnum.LOGIN_ERROR.getCode(),ResultEnum.LOGIN_ERROR.getMsg());
    }

    /**
     * 注册用户
     */
    @PostMapping(value = "/user")
    public Result<Object> add(@Valid User user, @RequestParam(value = "file",required = false) MultipartFile file, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.insertUser(user,file);
    }
    /**
     * 搜索用户
     */
    @GetMapping(value = "/user/{userName}")
    public Result<User> select(@PathVariable("userName") String userName){
        return ResultUtil.success(userService.findUserByUserName(userName));
    }


    /**
     * 修改用户
     */
    @PutMapping(value = "/user/update")
    public Result<Object> update(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam("userName") String userName){
        return userService.updateUser(userName,file);
    }

}
