package com.example.musicdemo.service;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.Song;
import com.example.musicdemo.entity.User;
import com.example.musicdemo.enums.ResultEnum;
import com.example.musicdemo.exception.SongException;
import com.example.musicdemo.interceptor.Constant;
import com.example.musicdemo.repository.UserRepository;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public int checkLogin(User user) throws SongException{
        User userOptional = userRepository.findByUserName(user.getUserName());
        if(userOptional == null){
            return ResultEnum.LOGIN_ERROR_NULL.getCode();
        }else if(!userOptional.getPassWord().equals(user.getPassWord())){
            return ResultEnum.LOGIN_ERROR.getCode();
        }else{
            user.setPassWord(null);
            user.setUserName(userOptional.getUserName());
            return 200;
        }
    }
    public User getUser(HttpSession session){
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        return user;
    }

    /**
     * 查找用户
     */
    public User findUserByUserName(String userName) throws SongException{
        User user = userRepository.findByUserName(userName);
        user.setPassWord(null);
        return user;
    }

    /**
     * 注册用户
     */
    public Result<Object> insertUser(User user, MultipartFile file) throws SongException{
        User baseUser = userRepository.findByUserName(user.getUserName());
        if(baseUser != null){
            return ResultUtil.error(ResultEnum.LOGIN_ERROR_HAVE.getCode(),ResultEnum.LOGIN_ERROR_HAVE.getMsg());
        }
        user.setPassWord(user.getPassWord());
        user.setUserName(user.getUserName());
        try {
            int code = -1;
            if(file != null && file.getOriginalFilename() != null){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                if(".jpg".equals(suffix) || ".png".equals(suffix) || ".bmp".equals(suffix) || ".webp".equals(suffix) || ".tif".equals(suffix) || ".gif".equals(suffix) ){
                    code = 200;
                }
                if(code == 200) {
                    File clientFile = new File("C:/musicDemo/user/" + file.getOriginalFilename());
                    file.transferTo(clientFile);
                    user.setImg("http://47.107.232.78:8080/songPath/user/" + file.getOriginalFilename());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user);
        user.setPassWord(null);
        return ResultUtil.success(user);
    }

    /**
     * 修改用户
     */
    public Result<Object> updateUser(String userName,MultipartFile file) throws SongException{

        User baseUser = userRepository.findByUserName(userName);
        if(baseUser != null){
            try {
                int code = -1;
                if(file != null && file.getOriginalFilename() != null){
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    if(".jpg".equals(suffix) || ".png".equals(suffix) || ".bmp".equals(suffix) || ".webp".equals(suffix) || ".tif".equals(suffix) || ".gif".equals(suffix) ){
                        code = 200;
                    }
                    if(code == 200) {
                        File clientFile = new File("C:/musicDemo/user/" + file.getOriginalFilename());
                        file.transferTo(clientFile);
                        baseUser.setImg("http://47.107.232.78:8080/songPath/user/" + file.getOriginalFilename());
                        userRepository.save(baseUser);
                        return ResultUtil.success(baseUser);
                    }else{
                        return ResultUtil.error(ResultEnum.LOGIN_ERROR_IMAGE.getCode(),ResultEnum.LOGIN_ERROR_IMAGE.getMsg());
                    }
                }else{
                    userRepository.save(baseUser);
                    return ResultUtil.error(ResultEnum.LOGIN_ERROR_IMAGE.getCode(),ResultEnum.LOGIN_ERROR_IMAGE.getMsg());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultUtil.error(ResultEnum.LOGIN_ERROR_NULL.getCode(),ResultEnum.LOGIN_ERROR_NULL.getMsg());
    }

}
