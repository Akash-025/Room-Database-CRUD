package com.example.animal_room_crud.animalDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    var noteName: String,
    var noteBody: String,
    var imageUri: String,
    var selectedItem : String,
    @PrimaryKey(autoGenerate = true)
    var noteId : Int = 0

)