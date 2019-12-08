package com.modelo.phygital.DAO

import androidx.room.*
import com.modelo.phygital.Entities.UserETY


@Dao
interface UserDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun AddUser(user : UserETY): Long

    @Update
    fun UpdateUser(user: UserETY)

    @Delete
    fun deleteUser(vararg User: UserETY)

    @Query("DELETE FROM user where id_user = :id")
    fun deleteUserById(id:Int)

    @Query("SELECT * FROM user WHERE user_name = :name and password = :password")
    fun getUserByName(name: String, password: String): UserETY?

//    @Query("SELECT * FROM user WHERE user_name = :name")
//    fun getUserByName(name: String): UserETY?

    @Query("SELECT * FROM user WHERE id_user= :id")
    fun getUserById(id:Int): UserETY

    @Query("SELECT*FROM user where user.is_logged= 1")
    fun getUserByIsLogged(): UserETY?

    @Query("SELECT*FROM user where user.is_logged= 1")
    fun getUserByIsLoggedNullable(): UserETY


    @Query("SELECT*FROM user")
    fun getAllUsers(): Array<UserETY>
}