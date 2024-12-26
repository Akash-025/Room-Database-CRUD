package com.example.animal_room_crud.viewModel

import com.example.animal_room_crud.animalDB.Note
import com.example.animal_room_crud.animalDB.NoteDatabase


class Repository(private val db : NoteDatabase) {

    suspend fun upsertNote(note: Note){
        db.dao.upsertNote(note)
    }

    suspend fun deleteNote(note: Note){
        db.dao.deleteNote(note)
    }

    suspend fun updateNote(note: Note){
        db.dao.updateNote(note)
    }

    fun getAllNotes() = db.dao.getAllNotes()
}