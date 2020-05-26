package com.example.bego.api_mvvm_retrofit.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Post.class} , version = 1)
public abstract class MyDB extends RoomDatabase{

    public abstract PostDao postDao();
}
