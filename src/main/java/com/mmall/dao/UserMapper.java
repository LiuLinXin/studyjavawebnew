package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserName(@Param("username") String username);

    User selectLogin(@Param("username") String userame, @Param("password")String password);

    int checkEmail(@Param("email") String email);

    String selectQuestionByUsername(@Param("userName") String userName);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updatePasswordByUsername(@Param(value = "username") String username, @Param(value = "passwordNew") String passwordNew);

    int checkPassword(@Param(value = "password") String password, @Param(value = "userId") Integer userId);

    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);

}