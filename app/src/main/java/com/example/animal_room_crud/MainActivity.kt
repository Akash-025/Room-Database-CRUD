package com.example.animal_room_crud


import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.animal_room_crud.CategorySpinner.CategorySpinner
import com.example.animal_room_crud.CatergorySpinner.SimplePopupExample
//import com.example.animal_room_crud.CatergorySpinner
import com.example.animal_room_crud.animalDB.Note
import com.example.animal_room_crud.animalDB.NoteDatabase
import com.example.animal_room_crud.ui.theme.Animal_Room_CRUDTheme
import com.example.animal_room_crud.viewModel.NoteViewModel
import com.example.animal_room_crud.viewModel.Repository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


class MainActivity : ComponentActivity() {


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            name = "note.db"
        ).build()
    }
    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(Repository(db)) as T
                }
            }
        }
    )


    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Animal_Room_CRUDTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF094675)
                ) {

                    var selectedNote by remember { mutableStateOf<Note?>(null) }

                    var isUpdate by remember {
                        mutableStateOf(false)
                    }

                    var name by remember {
                        mutableStateOf("")
                    }
                    var body by remember {
                        mutableStateOf("")
                    }
//start edit
                    var selectedImageUri by remember {
                        mutableStateOf<Uri?>(null)
                    }

                    var selectedItem by remember {
                        mutableStateOf("")
                    }




                    @OptIn(ExperimentalPermissionsApi::class)
                    val storagePermissionState = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
//
//                    var photoPickerLauncher = rememberLauncherForActivityResult(
//                        contract = ActivityResultContracts.GetContent()){
//                            uri: Uri? ->
//                        selectedImageUri = uri
//                    }

                    var photoPickerLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult = {
                            selectedImageUri = it
                        }
                    )


//end


                    var note = Note(
                        name,
                        body,
                        selectedImageUri.toString(),
                        selectedItem
                    )
                    var noteList by remember {
                        mutableStateOf(listOf<Note>())
                    }
                    viewModel.getNotes().observe(this) {
                        noteList = it
                    }
//                    //optional


                    //PopUp




                    Column(
                        Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        //PopUp



                        Text(
                            text = "Animal Details",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center

                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp)

                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),


                        ) {


                            OutlinedTextField(
                                value = name, onValueChange = {
                                    name = it

                                },
                                label = {
                                    Text(text = "Name")
                                },
                                modifier = Modifier.width(120.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CategorySpinner(selectedItem = selectedItem,
                                onItemSelected = { selectedItem = it })

                        }
                        //Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = body, onValueChange = {
                            body = it
                        },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Description")
                            }
                        )
                        //Spacer(modifier = Modifier.height(6.dp))

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Button(
                                onClick = {

                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                    storagePermissionState.launchPermissionRequest()
                                },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(56.dp)
                                ,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row {
//                                    Icon(imageVector = Icons.Default.Add, contentDescription ="" ,
//                                        modifier = Modifier.size(32.dp))
                                    Text(

                                        text = "Upload Photo"
                                    )
                                }



                            }
                            Spacer(modifier = Modifier.width(12.dp))

//Show image in image view
//                        AsyncImage(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(240.dp)
//                                .clip(RoundedCornerShape(8.dp)),
//
//                            model = selectedImageUri,
//                            contentDescription = null)

                            Button(onClick = {
                                val context = this@MainActivity

                                //viewModel.insertAnimal(animal)
                                if(selectedImageUri != null && name.isNotBlank() && body.isNotBlank()){

                                    var node = Note(
                                        noteId = selectedNote?.noteId ?: 0,
                                        noteName = name,
                                        noteBody = body,
                                        imageUri = selectedImageUri.toString(),
                                        selectedItem = selectedItem
                                    )
                                    if (isUpdate) {
                                        // Update Note
                                        selectedNote?.let {
                                            viewModel.updateNote(node)
                                        }
                                        isUpdate = false
                                        Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
                                        name = ""
                                        body = ""
                                        selectedImageUri = null
                                        selectedItem = ""
                                    }
                                    else{
                                        viewModel.upsertNote(note)
                                        Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show()
                                        name = ""
                                        body = ""
                                        selectedImageUri = null
                                        selectedItem = ""
                                    }



                                }
                                else{
                                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                }



                            },
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(56.dp),
                                shape = RoundedCornerShape(8.dp)
                            )

                            {

                                Text(
                                    text = if(isUpdate) "Update" else "Save",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )

                            }

                        }

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(8.dp)),

                            model = selectedImageUri,
                            contentDescription = null)





                        LazyColumn {
                            items(noteList) { note ->

                                //PopUp
                                var showPopup by remember { mutableStateOf(true) }
                                //Check
                                var check by remember {mutableStateOf(false)}

                                Column(Modifier.clickable {
                                    //viewModel.deleteNote(note)
                                }) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(6.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        elevation = CardDefaults.cardElevation(4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFF009688),
                                        )
                                    ) {

                                        Row(modifier = Modifier.padding(6.dp)) {

                                            Card {

                                                if (note.imageUri.isNotBlank()) {
                                                    ImageFromUri(Uri.parse(note.imageUri))
                                                } else {
                                                    Text("No Image")
                                                }

                                            }

                                            Spacer(modifier = Modifier.width(20.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(text = "Name: ${note.noteName}")
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text(text = "Category: ${note.selectedItem}")
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text(text = "Description: ${note.noteBody}")
                                            }

                                        }
                                        Row(modifier = Modifier
                                            .padding(6.dp)
                                            .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center) {

                                            Button(
                                                onClick = {


                                                    //id = note.noteId
                                                    name = note.noteName
                                                    body = note.noteBody
                                                    selectedItem = note.selectedItem
                                                    selectedImageUri = Uri.parse(note.imageUri)
                                                    selectedNote = note

                                                    //viewModel.updateNote(note)

                                                    isUpdate = true


                                                },
                                                shape = RoundedCornerShape(5.dp)
                                            ) {

                                                Text(text = "Update")

                                            }

                                            Spacer(modifier = Modifier.width(24.dp))

                                            Button(

//                                                onClick = {
//                                                    val context = this@MainActivity
//                                                    viewModel.deleteNote(note)
//                                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
//
//                                                },

                                                onClick = {


                                                    check = true
                                                    showPopup = true

                                                },
                                                modifier = Modifier,
                                                shape = RoundedCornerShape(5.dp)
                                            ) {

                                                Text(text = "Delete")


                                            }
                                            if(check){
                                                if (showPopup) {



                                                        Popup(
                                                            alignment = Alignment.Center,
                                                            onDismissRequest = {
                                                                showPopup = false
                                                            },

                                                            ) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .size(200.dp)
                                                                    .background(
                                                                        Color.White,
                                                                        shape = RoundedCornerShape(
                                                                            12.dp
                                                                        )
                                                                    )
                                                                    //.clickable { showPopup = false } // Close the popup when clicked
                                                                    .padding(16.dp)
                                                            ) {
                                                                Column {
                                                                    Text(
                                                                        text = "Do you want to delete?",
                                                                        style = MaterialTheme.typography.bodyLarge,
                                                                        //modifier = Modifier.align(Alignment.Center)
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            12.dp
                                                                        )
                                                                    )
                                                                    Row {
                                                                        Button(
                                                                            onClick = {

                                                                                check = false
                                                                                showPopup = false

                                                                                val context =
                                                                                    this@MainActivity
                                                                                viewModel.deleteNote(
                                                                                    note
                                                                                )
                                                                                Toast.makeText(
                                                                                    context,
                                                                                    "Deleted successfully",
                                                                                    Toast.LENGTH_SHORT
                                                                                ).show()
                                                                            },
                                                                            colors = ButtonDefaults.buttonColors(
                                                                                containerColor = Color.Red
                                                                            )
                                                                        ) {
                                                                            Text(text = "YES")

                                                                        }
                                                                        Spacer(
                                                                            modifier = Modifier.width(
                                                                                12.dp
                                                                            )
                                                                        )
                                                                        Button(
                                                                            onClick = {
                                                                                showPopup = false
                                                                            },
                                                                            colors = ButtonDefaults.buttonColors(
                                                                                containerColor = Color.Green
                                                                            )
                                                                        ) {
                                                                            Text(text = "NO")

                                                                        }
                                                                    }

                                                                }

                                                            }

                                                        //check = false
                                                    }
                                                }

                                            }

                                        }
//                                    Text(text = "Name: ${note.noteName}")
//                                    Spacer(modifier = Modifier.height(6.dp))
//                                    Text(text = "Description: ${note.noteBody}")
//                                    Divider(
//                                        Modifier
//                                            .fillMaxWidth()
//                                            .padding(6.dp))

                                        //Image(painter = note.imageUri, contentDescription = "")
                                        //NoteItem(note)

                                    }
                                }
                            }
                        }


//

                    }
                }

            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun ImageFromUri(uri: Uri) {
    var painter: Painter = rememberAsyncImagePainter(model = uri)


        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

}


