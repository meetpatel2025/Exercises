package com.training.formapp.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
@Composable
fun ImagePicker(
    imageRes: String?,
    onImageSelected: (String) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            onImageSelected(it.toString())
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        // 👇 Add Image Label
        Text(
            text = "Add Image",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (!imageRes.isNullOrEmpty()) {
            AsyncImage(
                model = imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("No Image")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            launcher.launch(arrayOf("image/*"))
        }) {
            Text("Select Image")
        }
    }
}