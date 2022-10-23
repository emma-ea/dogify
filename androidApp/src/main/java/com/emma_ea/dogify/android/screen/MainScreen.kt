package com.emma_ea.dogify.android.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emma_ea.dogify.android.viewmodel.MainViewModel
import com.emma_ea.dogify.model.Breed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

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
           Column(
               Modifier
                   .fillMaxSize()
                   .padding(8.dp)
           ) {
              Row(
                  Modifier
                      .wrapContentWidth(Alignment.End)
                      .padding(8.dp)
              ) {
                  Text(text = "Filter Favourites")
                  Switch(
                      checked = shouldFilterFavourites,
                      modifier = Modifier
                          .padding(horizontal = 8.dp),
                      onCheckedChange = {
                          viewModel.onToggleFavouriteFilter()
                      }
                  )
              }
               when (state) {
                   MainViewModel.State.LOADING -> {
                       Spacer(Modifier.weight(1f))
                       CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                       Spacer(Modifier.weight(1f))
                   }

                   MainViewModel.State.NORMAL -> Breeds(
                       breeds = breeds,
                       onFavouriteTapped = viewModel::onFavouriteTapped
                   )

                   MainViewModel.State.EMPTY -> {
                       Spacer(Modifier.weight(1f))
                       Text(
                           text = "Oops looks like there are no ${if (shouldFilterFavourites) "favourites" else "dogs" }",
                           modifier = Modifier.align(Alignment.CenterHorizontally)
                       )
                       Spacer(Modifier.weight(1f))
                   }

                   MainViewModel.State.ERROR -> {
                       Spacer(Modifier.weight(1f))
                       Text(
                           text = "Oops something went wrong...",
                           modifier = Modifier.align(Alignment.CenterHorizontally)
                       )
                       Spacer(Modifier.weight(1f))
                   }
               }

               if (events == MainViewModel.Event.ERROR) {
                   snackbarCoroutineScope.launch {
                       scaffoldState.snackbarHostState.apply {
                           currentSnackbarData?.dismiss()
                           showSnackbar("Oops something went wrong...")
                       }
                   }
               }
           } 
        }
    }
}

@Composable
fun Breeds(breeds: List<Breed>, onFavouriteTapped: (Breed) -> Unit = {}) {

}