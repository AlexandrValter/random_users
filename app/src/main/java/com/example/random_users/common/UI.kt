package com.example.random_users.common

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.random_users.R
import com.example.random_users.model.User
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AmountCard(
    selected: Boolean,
    textRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(50.dp),
        shape = CircleShape,
        backgroundColor = if (selected) ColorLightBlue else Color.White,
        elevation = 0.dp,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = textRes),
                fontWeight = if (selected) FontWeight.W500 else FontWeight.W400
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier.size(70.dp),
                elevation = 0.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                GlideImage(
                    imageModel = user.mediumPicture,
                    contentScale = ContentScale.Crop,
                    contentDescription = "userPhoto",
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "${user.titleName} ${user.firstName} ${user.lastName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
                IconAndText(
                    iconRes = R.drawable.ic_address,
                    listOf(
                        user.streetNumber.toString().plus(" ${user.streetName}"),
                        user.city,
                        user.state,
                        user.postcode,
                        user.country
                    ).joinToString(separator = ", ")
                )
                IconAndText(iconRes = R.drawable.ic_phone, text = user.phone)
                IconAndText(iconRes = R.drawable.ic_cell, text = user.cell)
            }
        }
    }
}

@Composable
fun IconAndText(iconRes: Int, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(painter = painterResource(id = iconRes), contentDescription = "", Modifier.size(20.dp))
        Text(text = text)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardIconAndText(
    iconRes: Int,
    text: String,
    fontSize: TextUnit = 16.sp,
    clickable: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        onClick = onClick,
        enabled = clickable
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "e-mail"
            )
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Composable
fun ErrorDialog(
    dialogState: Boolean,
    text: String,
    context: Context,
    onDialogStateChange: ((Boolean) -> Unit)? = null,
    onClickButton: () -> Unit
) {
    if (dialogState) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            text = {
                Column {
                    Text(
                        text = text,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            shape = RoundedCornerShape(10.dp),
            buttons = {
                Row {
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            (context as? ComponentActivity)?.finishAffinity()
                        }
                    ) {
                        Text(
                            text = "Close app",
                        )
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClickButton()
                        }
                    ) {
                        Text(
                            text = "Try again",
                        )
                    }
                }
            },
            onDismissRequest = { onDialogStateChange?.invoke(false) }
        )
    }
}