package com.modelo.phygital

import android.content.Context
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import com.modelo.phygital.DAO.*
import com.modelo.phygital.Entities.*

@Database(
    entities = [
         UserETY::class

    ], version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun UserDAO(): UserDAO



    companion object {
        private var INSTANCE: AppDatabase? = null
        lateinit var context: Context
        private lateinit var currentUser : UserETY
        private lateinit var newUser : UserETY

        fun getLoginUser() = currentUser

        fun getNewUser() = newUser
        fun setNewUser(registering: UserETY) {
            newUser = registering
        }

        fun getCurrentUser() = currentUser
        fun setCurrentUser(user: UserETY) {
            currentUser = user
        }

        fun getAppDatabase(contxt: Context): AppDatabase {
            if (INSTANCE == null) {
                context = contxt
                INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase::class.java,
                    "fisio.db"
                )
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            initializeData(db)
                        }
                    })
                    .build()
            }

            return INSTANCE as AppDatabase
        }

        fun initializeData(db: SupportSQLiteDatabase) {

            db.execSQL("INSERT INTO user VALUES (1,'fe', '1', 0)")


        }
    }
}