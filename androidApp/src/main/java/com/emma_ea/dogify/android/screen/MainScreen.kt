package com.emma_ea.dogify.android.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.emma_ea.dogify.android.R
import com.emma_ea.dogify.android.viewmodel.MainViewModel
import com.emma_ea.dogify.model.Breed
import com.emma_ea.dogify.utils.DispatcherProvider
import com.google.accompanist.coil.CoilPainterDefaults
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
                  modifier = Modifier
                      .wrapContentWidth(Alignment.End)
                      .padding(8.dp),
                  verticalAlignment = Alignment.CenterVertically
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
                  Spacer(Modifier.weight(1f))
                  Text(text = "Count: ${viewModel.favouritesCount.value}")
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

               if (isRefreshing) {
                   snackbarCoroutineScope.launch {
                       scaffoldState.snackbarHostState.apply {
                           currentSnackbarData?.dismiss()
                           showSnackbar("Clearing database. Loading new data...")
                       }
                   }
               }
           } 
        }
    }
}

@Composable
fun Breeds(breeds: List<Breed>, onFavouriteTapped: (Breed) -> Unit = {}) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(breeds) {
            Column(Modifier.padding(8.dp)) {
                Image(
                    painter = rememberCoilPainter(
                        request = it.imageUrl,
                        fadeIn = true,
                        requestBuilder = {
                            crossfade(true)
                            placeholder(R.drawable.ic_pets)
                            error(R.drawable.ic_pets)
                            dispatcher(Dispatchers.IO)
                        }
                    ),
                    contentDescription = "${it.name}-image",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop,
                )
                Row(Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        if (it.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Mark as favourite",
                        modifier = Modifier.clickable {
                            onFavouriteTapped(it)
                        }
                    )
                }
            }
        }
    }
}