package com.example.bego.api_mvvm_retrofit.RoomDB;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PostDao {

    @Insert
     void addPosts(Post posts);

    @Query("SELECT * FROM posts")
    List<Post> getAllPosts();

    @Query("DELETE FROM posts")
     void restPostTable();

}
