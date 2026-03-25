package com.training.formapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.training.formapp.model.User
import com.training.formapp.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavController,
    viewModel: UserViewModel,
    userId: Int?
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var imageRes by remember { mutableStateOf("") }

    val isEdit = userId != null

    LaunchedEffect(userId) {
        userId?.let {
            viewModel.loadUser(it)
        }
    }

    val user = viewModel.selectedUser

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            email = it.email
            imageRes = it.imageRes
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Edit User" else "Add User") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ImagePicker(
                imageRes = imageRes,
                onImageSelected = {
                    imageRes = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (isEdit) {
                    viewModel.updateUser(
                        User(userId!!, name, email, imageRes)
                    )
                    Toast.makeText(context, "User updated", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addUser(
                        User(name = name, email = email, imageRes = imageRes)
                    )
                    Toast.makeText(context, "User added", Toast.LENGTH_SHORT).show()
                }
                navController.popBackStack()
            }) {
                Text(if (isEdit) "Update" else "Save")
            }
        }
    }
}