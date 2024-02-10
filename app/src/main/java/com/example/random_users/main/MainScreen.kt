package com.example.random_users.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.random_users.R
import com.example.random_users.common.*
import com.example.random_users.compose.userBuild
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

enum class AmountUsers {
    TEN, TWENTY_FIVE, FIFTY
}

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainViewModel = getViewModel()

    val users by viewModel.users.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isError by viewModel.isError.collectAsState()

    val context = LocalContext.current
    val appPreferences = AppPreferences(context)

    var selectedAmount: AmountUsers by remember {
        mutableStateOf(appPreferences.amountUsers.toAmountUsers() ?: AmountUsers.TEN)
    }
    var showAllAmounts by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = selectedAmount) {
        viewModel.loadUsers(selectedAmount.toInt())
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { scope.launch { viewModel.onRefresh(selectedAmount.toInt()) } },
        swipeEnabled = users.isNotEmpty()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorLightGrey)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.label_show),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    AnimatedVisibility(visible = showAllAmounts || selectedAmount == AmountUsers.TEN) {
                        AmountCard(
                            selected = selectedAmount == AmountUsers.TEN,
                            textRes = R.string.label_10,
                        ) {
                            if (showAllAmounts) {
                                selectedAmount = AmountUsers.TEN
                                appPreferences.amountUsers = selectedAmount.toInt()
                                showAllAmounts = false
                            } else
                                showAllAmounts = true
                        }
                    }
                    AnimatedVisibility(visible = showAllAmounts || selectedAmount == AmountUsers.TWENTY_FIVE) {
                        AmountCard(
                            selected = selectedAmount == AmountUsers.TWENTY_FIVE,
                            textRes = R.string.label_25
                        ) {
                            if (showAllAmounts) {
                                selectedAmount = AmountUsers.TWENTY_FIVE
                                appPreferences.amountUsers = selectedAmount.toInt()
                                showAllAmounts = false
                            } else
                                showAllAmounts = true
                        }
                    }
                    AnimatedVisibility(visible = showAllAmounts || selectedAmount == AmountUsers.FIFTY) {
                        AmountCard(
                            selected = selectedAmount == AmountUsers.FIFTY,
                            textRes = R.string.label_50
                        ) {
                            if (showAllAmounts) {
                                selectedAmount = AmountUsers.FIFTY
                                appPreferences.amountUsers = selectedAmount.toInt()
                                showAllAmounts = false
                            } else
                                showAllAmounts = true
                        }
                    }
                }


            }
            LazyColumn() {
                itemsIndexed(users) { index, item ->
                    if (index == 0)
                        Spacer(modifier = Modifier.height(8.dp))
                    UserCard(user = item) {
                        scope.launch {
                            val userId: Int? =
                                item.id.takeIf { it != 0 } ?: viewModel.getUserId(item)
                            userId?.let { navController.navigate(userBuild(it)) }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        ErrorDialog(
            dialogState = isError.isNotEmpty(),
            text = isError,
            context = context
        ) {
            scope.launch {
                viewModel.loadUsers(selectedAmount.toInt())
            }
        }
    }
}
