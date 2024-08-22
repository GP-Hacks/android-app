package com.example.places

import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.example.ui.theme.mColors
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

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
                    Text(text = "Татарстан. Места", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
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
                        color = Color.White
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
                            color = Color.White
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
            })
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
            })
        }
    }
}

@Composable
fun PlacesListColumn(
    listPlaces: ResultModel<List<PlaceModel>>,
    listState: LazyListState,
    onBuyTicket: (Int, Long, Long) -> Unit
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
            }
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
    onBuyTicket: (Int, Long, Long) -> Unit
) {

    var isOpenDialog by remember { mutableStateOf(false) }
    var currentItem by remember { mutableStateOf(PlaceModel(0, "", "", 0.0, 0.0, "", "", "", "", emptyList(), 0, emptyList())) }

    if (isOpenDialog) {
        FullInfoPlaceDialog(
            place = currentItem,
            onDismissRequest = {
                isOpenDialog = false
            },
            onBuyTicket = { d, t ->
                onBuyTicket(currentItem.id, d, t)
            }
        )
    }

    var mapView by remember {
        mutableStateOf<MapView?>(null)
    }
    val context = LocalContext.current

    AndroidView(
        factory = { t ->
            MapKitFactory.initialize(t)
            MapView(t).apply {
                mapWindow.map.move(
                    CameraPosition(
                        Point(55.79718, 49.106453),
                        9.0f,
                        150.0f,
                        30.0f
                    )
                )
                onStart()
                MapKitFactory.getInstance().onStart()
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { it ->
            mapView = it
            val mapObjects = it.mapWindow.map.mapObjects
            mapObjects.clear()

            if (listPlaces.data != null && listPlaces.status == ResultModel.Status.SUCCESS) {
                listPlaces.data!!.forEach { place ->
                    val vectorDrawable = ContextCompat.getDrawable(context, R.drawable.location_point_icon)
                    val bitmap = Bitmap.createBitmap(
                        vectorDrawable!!.intrinsicWidth,
                        vectorDrawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(bitmap)
                    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
                    vectorDrawable.draw(canvas)

                    val imageProvider = ImageProvider.fromBitmap(bitmap)

                    val placemark = mapObjects.addPlacemark(Point(place.latitude, place.longitude))
                    placemark.setIcon(imageProvider)

                    placemark.addTapListener { _, _ ->
                        isOpenDialog = true
                        currentItem = place
                        true
                    }
                }
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            mapView?.onStop()
        }
    }
}