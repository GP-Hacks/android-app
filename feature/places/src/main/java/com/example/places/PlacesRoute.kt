package com.example.places

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.model.ResultModel
import com.example.domain.model.PlaceModel
import com.example.places.components.FullInfoPlaceDialog
import com.example.places.components.PlaceCard
import com.example.places.components.AnimatedTab
import com.example.ui.theme.evolentaFamily
import com.example.ui.theme.mColors
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlin.math.roundToInt

@Composable
fun PlacesRoute(
    viewModel: PlacesViewModel = hiltViewModel()
) {
    val listPlaces by viewModel.listPlaces.collectAsState()

    var selectedType by remember {
        mutableIntStateOf(0)
    }

    val listState = rememberLazyListState()
    var paddingState by remember { mutableStateOf(80.dp) }
    val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    LaunchedEffect(Unit) {
        viewModel.loadPlacesCategories()
        viewModel.loadPlaces()
    }

    LaunchedEffect(firstVisibleItemIndex.value) {
        val maxPadding = 80.dp
        val minPadding = 16.dp

        paddingState = when (selectedType) {
            0 -> {
                if (firstVisibleItemIndex.value > 0) {
                    minPadding
                } else {
                    maxPadding
                }
            }
            1 -> {
                minPadding
            }
            else -> maxPadding
        }
    }
    val paddingAnimation by animateDpAsState(targetValue = paddingState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF00B545),
                    if (selectedType == 0) {
                        RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                    } else {
                        RoundedCornerShape(0.dp)
                    }
                )
                .padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.TopStart
            ) {
                if (paddingAnimation == 80.dp) {
                    Image(painter = painterResource(id = R.drawable.background_image_green), contentDescription = null, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Spacer(Modifier.height(paddingAnimation))
                    Text(text = "Татарстан. Места", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold, fontFamily = evolentaFamily)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.currentSearch.value,
                        onValueChange = {
                            viewModel.updateSearch(it)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedBorderColor = Color.White,
                            focusedTrailingIconColor = Color.White,
                            unfocusedTrailingIconColor = Color.White,
                            focusedLeadingIconColor = Color.White,
                            unfocusedLeadingIconColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            selectionColors = TextSelectionColors(
                                handleColor = Color.White,
                                backgroundColor = Color(0xFF008935)
                            )
                        ),
                        shape = RoundedCornerShape(20.dp),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        },
                        singleLine = true,
                        maxLines = 1
                    )
                    Spacer(Modifier.height(16.dp))
                    AnimatedTab(
                        items = listOf("Список", "Карта"),
                        modifier = Modifier
                            .padding(start = 80.dp, end = 80.dp)
                            .height(25.dp),
                        selectedItemIndex = selectedType,
                        onSelectedTab = {
                            selectedType = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            val categoriesList = viewModel.listPlacesCategories.collectAsState()
            LazyRow {
                item {
                    Text(
                        text = "Все",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .background(
                                if (viewModel.currentCategory.value == "all") Color(
                                    0xFF008935
                                ) else mColors.primary, RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                viewModel.changeCategory("all")
                                viewModel.loadPlaces()
                            }
                            .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                        color = Color.White, fontFamily = evolentaFamily
                    )
                }

                categoriesList.value.data?.forEach {
                    item {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .background(
                                    if (viewModel.currentCategory.value == it) Color(
                                        0xFF008935
                                    ) else mColors.primary, RoundedCornerShape(6.dp)
                                )
                                .clickable {
                                    viewModel.changeCategory(it)
                                    viewModel.loadPlaces()
                                }
                                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                            color = Color.White, fontFamily = evolentaFamily
                        )
                    }
                }
            }
        }
        val context = LocalContext.current
        if (selectedType == 0) {
            PlacesListColumn(listPlaces, listState, onBuyTicket = { i, d, t ->
                viewModel.buyTicket(
                    i,
                    d,
                    t,
                    onSuccess = {
                        Toast.makeText(context, "Покупка прошла успешно", Toast.LENGTH_LONG).show()
                    },
                    onFailure = {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                )
            }, isAuth = viewModel.checkAuth())
        } else {
            PlacesMap(listPlaces, onBuyTicket = { i, d, t ->
                viewModel.buyTicket(
                    i,
                    d,
                    t,
                    onSuccess = {
                        Toast.makeText(context, "Покупка прошла успешно", Toast.LENGTH_LONG).show()
                    },
                    onFailure = {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                )
            }, isAuth = viewModel.checkAuth())
        }
    }
}

@Composable
fun PlacesListColumn(
    listPlaces: ResultModel<List<PlaceModel>>,
    listState: LazyListState,
    onBuyTicket: (Int, Long, Long) -> Unit,
    isAuth: Boolean
) {

    var isOpenDialog by remember {
        mutableStateOf(false)
    }
    var currentItem by remember {
        mutableStateOf(PlaceModel(0, "", "", 0.0, 0.0, "", "", "", "", emptyList(), 0, emptyList()))
    }

    if (isOpenDialog) {
        FullInfoPlaceDialog(
            place = currentItem,
            onDismissRequest = {
                isOpenDialog = false
            },
            onBuyTicket = { d, t ->
                onBuyTicket(currentItem.id, d, t)
            },
            isAuth = isAuth
        )
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (listPlaces.status) {
            ResultModel.Status.SUCCESS -> {
                listPlaces.data?.forEach {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        if (it.photos.isEmpty()) {
                            PlaceCard(
                                image = "",
                                title = it.name,
                                geo = it.location,
                                category = it.category,
                                site = if (it.website != "") it.website else null,
                                openDialog = {
                                    currentItem = it
                                    isOpenDialog = true
                                },
                                cost = it.cost
                            )
                        } else {
                            PlaceCard(
                                image = it.photos[0],
//                                image = "",
                                title = it.name,
                                geo = it.location,
                                category = it.category,
                                site = if (it.website != "") it.website else null,
                                openDialog = {
                                    currentItem = it
                                    isOpenDialog = true
                                },
                                cost = it.cost
                            )
                        }
                    }
                }
            }
            ResultModel.Status.LOADING -> {
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    CircularProgressIndicator(
                        color = mColors.primary
                    )
                }
            }
            else -> {}
        }
    }
}

@Composable
fun PlacesMap(
    listPlaces: ResultModel<List<PlaceModel>>,
    onBuyTicket: (Int, Long, Long) -> Unit,
    isAuth: Boolean
) {
    var isOpenDialog by remember { mutableStateOf(false) }
    var currentItem by remember { mutableStateOf(0) }

    if (isOpenDialog) {
        FullInfoPlaceDialog(
            place = listPlaces.data!![currentItem],
            onDismissRequest = { isOpenDialog = false },
            onBuyTicket = { d, t -> onBuyTicket(listPlaces.data!![currentItem].id, d, t) },
            isAuth = isAuth
        )
    }

    var mapView by remember { mutableStateOf<MapView?>(null) }
    val context = LocalContext.current

    // Pre-create the bitmap for the placemark
    val bitmap by remember {
        mutableStateOf(createBitmapForMarker(context))
    }

    // Manage listeners state and update it when listPlaces.data changes
    val listeners = remember(listPlaces.data) {
        listPlaces.data?.mapIndexed { index, place ->
            object : MapObjectTapListener {
                override fun onMapObjectTap(p0: MapObject, p1: Point): Boolean {
                    Log.i("MAP", "tap on ${place.id}")
                    currentItem = index
                    isOpenDialog = true
                    return true
                }
            }
        } ?: emptyList()
    }

    // Function to setup the map
    fun setupMap(mapView: MapView) {
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(55.79718, 49.106453),
                9.0f,
                150.0f,
                30.0f
            )
        )
    }

    // Function to update placemarks
    fun updateMap(mapView: MapView) {
        val mapObjects = mapView.mapWindow.map.mapObjects
        mapObjects.clear()

        listPlaces.data?.forEachIndexed { index, place ->
            val imageProvider = ImageProvider.fromBitmap(bitmap)
            mapObjects.addPlacemark {
                it.geometry = Point(place.latitude, place.longitude)
                it.setIcon(imageProvider)
                it.addTapListener(listeners[index])
            }
        }
    }

    AndroidView(
        factory = { context ->
            MapKitFactory.initialize(context)
            MapView(context).apply {
                setupMap(this)
                onStart()
                MapKitFactory.getInstance().onStart()
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = {
            mapView = it
            updateMap(it)
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            mapView?.onStop()
            MapKitFactory.getInstance().onStop()
            mapView = null
        }
    }
}

fun createBitmapForMarker(context: Context): Bitmap {
    val vectorDrawable = ContextCompat.getDrawable(context, R.drawable.location_point_icon)
    val bitmap = Bitmap.createBitmap(
        (vectorDrawable!!.intrinsicWidth * 1.5).roundToInt(),
        (vectorDrawable.intrinsicHeight * 1.5).roundToInt(),
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
}
