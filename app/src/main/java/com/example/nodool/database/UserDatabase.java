package com.example.nodool.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nodool.dao.UserDao;
import com.example.nodool.entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static final String dbName = "users";

    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context){

        if (userDatabase == null){
            userDatabase = Room.databaseBuilder(
                    context,
                    UserDatabase.class,
                    dbName
            ).build();
        }
        return userDatabase;

    }

    public abstract UserDao userDao();

}
