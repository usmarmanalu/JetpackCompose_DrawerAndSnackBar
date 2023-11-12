@file:OptIn(ExperimentalMaterial3Api::class)

package com.dicoding.mynavdrawer

import android.content.*
import android.widget.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import com.dicoding.mynavdrawer.data.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
class MyNavDrawerState(
    val drawerState: DrawerState,
    private val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
    private val context: Context
) {

    fun onMenuClick() {
        scope.launch {
            if (drawerState.isClosed) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }

    fun onItemSelected(item: MenuItem) {
        scope.launch {
            drawerState.close()
            val snackbarResult = snackbarHostState.showSnackbar(
                message = context.resources.getString(R.string.coming_soon, item.title),
                actionLabel = context.resources.getString(R.string.subscribe_question),
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                Toast.makeText(
                    context, context.resources.getString(R.string.subscribed_info),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun onBackPress() {
        if (drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        }
    }
}

@Composable
fun rememberMyNavDrawerState(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    },
    context: Context = LocalContext.current,
): MyNavDrawerState = remember(drawerState, coroutineScope, snackbarHostState, context) {
    MyNavDrawerState(drawerState, coroutineScope, snackbarHostState, context)

}
