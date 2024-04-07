package com.xnjz.one.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserInfoDao {

    @Insert
    void insertUserInfo(UserInfoBean userInfoBean);

    @Update
    void updateUserInfo(UserInfoBean userInfoBean);

    @Delete
    void deleteUserInfo(UserInfoBean userInfoBean);


    @Query("SELECT * FROM userInfoTable")
    List<UserInfoBean> getAllUserInfo();


    @Query("SELECT * FROM userInfoTable WHERE birthday = :idCard")
    UserInfoBean getUserInfo(String idCard);
}
