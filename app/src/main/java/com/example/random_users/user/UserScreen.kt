package com.example.random_users.user

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.random_users.R
import com.example.random_users.common.CardIconAndText
import com.example.random_users.common.ColorLightGrey
import com.example.random_users.model.Gender
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserScreen(id: Int, navController: NavHostController) {
    val viewModel: UserViewModel = getViewModel()

    val user by viewModel.user.collectAsState()

    val context = LocalContext.current
    val callPhoneNumber: (String) -> Unit = { phone ->
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        context.startActivity(intent)
    }

    LaunchedEffect(key1 = true) {
        viewModel.loadUser(id)
    }

    user?.let { person ->
        Scaffold(
            Modifier
                .fillMaxWidth(),
            topBar = {
                Row(modifier = Modifier.padding(top = 20.dp, start = 16.dp)) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "back button"
                        )
                    }
                }
            },
            backgroundColor = ColorLightGrey
        ) { it ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 0.dp, bottom = 16.dp)
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Card(
                            modifier = Modifier.size(150.dp),
                            shape = CircleShape,
                            elevation = 0.dp
                        ) {
                            GlideImage(
                                imageModel = person.largePicture,
                                contentScale = ContentScale.Crop,
                                contentDescription = "user photo",
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        elevation = 0.dp,
                        shape = RoundedCornerShape(20.dp),
                        backgroundColor = Color.White
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                when (person.gender) {
                                    Gender.MALE -> Icon(
                                        painter = painterResource(id = R.drawable.ic_male),
                                        contentDescription = "male",
                                        Modifier.size(30.dp)
                                    )
                                    Gender.FEMALE -> Icon(
                                        painter = painterResource(id = R.drawable.ic_female),
                                        contentDescription = "female",
                                        Modifier.size(30.dp)
                                    )
                                    else -> {}
                                }
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .weight(1f),
                                    text = "${person.titleName} ${person.firstName} ${person.lastName}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    textAlign = TextAlign.Center
                                )
                                person.nationality?.flagResId?.let {
                                    Image(
                                        imageVector = ImageVector.vectorResource(id = it),
                                        contentDescription = "flag",
                                        modifier = Modifier
                                            .height(32.dp)
                                            .width(48.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                    )
                                }
                            }
                            Row(modifier = Modifier.padding(top = 16.dp)) {
                                Text(
                                    text = stringResource(
                                        id = R.string.label_date_of_birthday,
                                        person.dateOfBirthday.dayOfMonth,
                                        person.dateOfBirthday.month.toString().lowercase(),
                                        person.dateOfBirthday.year,
                                        person.age
                                    )
                                )
                            }
                            Row(modifier = Modifier.padding(top = 8.dp)) {
                                Text(
                                    text = stringResource(
                                        id = R.string.label_registration_date,
                                        person.registrationDate.dayOfMonth,
                                        person.registrationDate.month.toString().lowercase(),
                                        person.registrationDate.year,
                                        person.registrationAge
                                    )
                                )
                            }
                        }
                    }
                    CardIconAndText(
                        iconRes = R.drawable.ic_phone,
                        text = person.phone,
                        clickable = true
                    ) {
                        callPhoneNumber(person.phone)
                    }
                    CardIconAndText(
                        iconRes = R.drawable.ic_cell,
                        text = person.cell,
                        clickable = true
                    ) {
                        callPhoneNumber(person.cell)
                    }
                    CardIconAndText(
                        iconRes = R.drawable.ic_email,
                        text = person.email,
                        clickable = true
                    ) {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse("mailto:${person.email}")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                "Mail application not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        elevation = 0.dp,
                        shape = RoundedCornerShape(20.dp),
                        backgroundColor = Color.White,
                        onClick = {
                            val label = "Location"
                            val geoUri =
                                "geo:${person.latitude},${person.longitude}?q=$label@${person.latitude},${person.longitude}"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                            if (intent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Map application not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.title_location),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            Text(
                                text = person.streetNumber.toString().plus(" ${person.streetName}"),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            )
                            Text(
                                text = person.city.plus(", ${person.state}"),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            )
                            Text(
                                text = person.postcode,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            )
                            Text(
                                text = person.country,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            )
                        }
                    }
                    CardIconAndText(
                        iconRes = R.drawable.ic_clock,
                        text = person.timezoneDescription.plus(" (GMT${person.timezoneOffset})"),
                        fontSize = 14.sp,
                        clickable = false
                    ) {}
                }
            }
        }
    }
}