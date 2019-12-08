package com.modelo.phygital.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user",indices = [Index(value = ["user_name"], unique = true)])
data class UserETY(
    @field:ColumnInfo(name = "user_name") var user_name: String,
    @field:ColumnInfo(name = "password") var password: String,
    @field:ColumnInfo(name = "is_logged") var is_logged: Int

){
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id_user") var id_user: Int = 0
}