package com.example.stocks

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
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.model.ResultModel
import com.example.domain.model.PartnersModel
import com.example.stocks.components.FullInfoPartnerDialog
import com.example.stocks.components.PartnerCard
import com.example.ui.theme.mColors

@Composable
fun StocksRoute(
    viewModel: StocksViewModel = hiltViewModel()
) {
    val listPartners by viewModel.listPartners.collectAsState()

    val listState = rememberLazyListState()
    var paddingState by remember { mutableStateOf(80.dp) }
    val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
        viewModel.loadPartners()
    }

    LaunchedEffect(firstVisibleItemIndex.value) {
        val maxPadding = 80.dp
        val minPadding = 16.dp

        paddingState = if (firstVisibleItemIndex.value > 0) {
            minPadding
        } else {
            maxPadding
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
                    Color(0xFFE13437),
                    RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                )
                .padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.TopStart
            ) {
                if (paddingAnimation == 80.dp) {
                    Image(painter = painterResource(id = R.drawable.background_image_red), contentDescription = null, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Spacer(Modifier.height(paddingAnimation))
                    Text(text = "Акции", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
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
                }
            }
            val categoriesList by viewModel.listCategories.collectAsState()
            LazyRow {
                item {
                    Text(
                        text = "Все",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .background(
                                if (viewModel.currentCategory.value == "all") Color(
                                    0xFFCB1E21
                                ) else Color.Transparent, RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                viewModel.changeCategory("all")
//                                viewModel.loadPlaces()
                            }
                            .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                        color = Color.White
                    )
                }

                categoriesList.data?.forEach {
                    item {
                        Text(
                            text = it.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .background(
                                    if (viewModel.currentCategory.value == it.title) Color(
                                        0xFFCB1E21
                                    ) else Color.Transparent, RoundedCornerShape(6.dp)
                                )
                                .clickable {
                                    viewModel.changeCategory(it.title)
            //                                    viewModel.loadPlaces()
                                }
                                .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
        PartnersListColumn(listPartners, listState)
    }
}

@Composable
fun PartnersListColumn(
    listPartners: ResultModel<List<PartnersModel>>,
    listState: LazyListState
) {

    var isOpenDialog by remember {
        mutableStateOf(false)
    }
    var currentItem by remember {
        mutableStateOf(0)
    }

    if (isOpenDialog) {
        FullInfoPartnerDialog(partner = listPartners.data!![currentItem]) {
            isOpenDialog = false
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (listPartners.status) {
            ResultModel.Status.SUCCESS -> {
                listPartners.data?.forEachIndexed { index, it ->
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        PartnerCard(
                            image = it.backgroundImage,
                            title = it.title,
                            category = it.category.title,
                            categoryColor = it.category.color,
                            date = it.dateEnd,
                            value = if (it.cashBack != null) "Кэшбэк ${it.cashBack}%" else if (it.percentSale != null) "Скидка ${it.percentSale}%" else null,
                            openDialog = {
                                currentItem = index
                                isOpenDialog = true
                            }
                        )
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