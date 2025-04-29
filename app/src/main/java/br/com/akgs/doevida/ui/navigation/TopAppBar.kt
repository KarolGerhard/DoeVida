package br.com.akgs.doevida.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.R

@Composable
fun TopAppBar(label: String, onBack: () -> Unit) {
    NavigationBar(
        containerColor = Color(0xFF95313B),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBack() }
            .height(64.dp),

        ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(18.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
            )
        )
    }
}