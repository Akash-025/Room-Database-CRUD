package com.example.animal_room_crud.CatergorySpinner


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun SimplePopupExample() {
    var showPopup by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        Alignment.Center
    ) {
//        Button(onClick = { showPopup = true }) {
//            Text("Show Popup")
//        }

        if (showPopup) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { showPopup = false }
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .clickable { showPopup = false } // Close the popup when clicked
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Do you want delete",
                            style = MaterialTheme.typography.bodyLarge,
                            //modifier = Modifier.align(Alignment.Center)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row {
                            Button(onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red)
                            ) {
                                Text(text = "YES")

                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Green)
                            ) {
                                Text(text = "NO")

                            }
                        }

                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun sample(){
    SimplePopupExample()
}