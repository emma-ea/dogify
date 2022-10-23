package com.emma_ea.dogify.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.emma_ea.dogify.android.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    val state by viewModel.state.collectAsState()
    val breeds by viewModel.breeds.collectAsState()
    val events by viewModel.events.collectAsState(Unit)
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val shouldFilterFavourites by viewModel.shouldFilterFavourites.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = viewModel::refresh
        ) {
            
        }
    }

}

@Composable
fun CustomText(
    text: String
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 48.sp,
                    fontStyle = FontStyle.Italic
                )
            ) {
                append(text[0].uppercase())
            }
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(text.substring(1))
            }
        },
        color = Color.DarkGray,
        fontSize = 30.sp
    )
}