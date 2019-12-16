package com.modelo.phygital.Class

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataBaseFireBase {

    private companion object ConnectionFireBase {
        private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    }
/*
     fun getUsersReference() : DatabaseReference{
        return database.getReference("app").child("users")
    }

    fun getListReference(user:UserFis) :DatabaseReference{
        return database.getReference("app").child("users").child(user.id).child("listasLocales")
    }
    fun getParticulaUserReference(idUser : String) : DatabaseReference{
        return getUsersReference().child(idUser)
    }
*/
}