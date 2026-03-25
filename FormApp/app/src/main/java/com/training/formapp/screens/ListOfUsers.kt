package com.training.formapp.screens

//import androidx.paging.compose.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.training.formapp.model.User
import com.training.formapp.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: UserViewModel
) {
    val users = viewModel.users.collectAsLazyPagingItems()
    var showDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("All Users")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") }
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->

        if (users.itemCount == 0 &&
            users.loadState.refresh is LoadState.NotLoading
        ) {
            EmptyState()
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {

                items(users.itemCount) { index ->
                    val user = users[index]
                    user?.let {
                        UserItem(
                            user = it,
                            onClick = {
                                navController.navigate("edit/${it.id}")
                            },
                            onDelete = {
                                selectedUser = user
                                showDialog = true
//                                viewModel.deleteUser(it)
                            }

                        )
                    }
                }

                if (users.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
            if (showDialog && selectedUser != null) {
                DeleteDialog(
                    onConfirm = {
                        viewModel.deleteUser(selectedUser!!)
                        showDialog = false
                        selectedUser = null
                    },
                    onDismiss = {
                        showDialog = false
                        selectedUser = null
                    }
                )
            }
        }
    }
}