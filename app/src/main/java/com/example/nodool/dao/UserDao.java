package com.example.nodool.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nodool.entities.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void registerUser(User user);

    @Query("SELECT * FROM users WHERE email=(:email) and password=(:password)")
    User loginUser(String email, String password);

    @Query("SELECT * FROM users WHERE email=(:email)")
    User fetchUser(String email);
}
