package com.example.design_system.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.design_system.R
import com.example.design_system.shimmer.NeuracrShimmer
import com.example.design_system.theming.NeuracrTheme

@Composable
fun NeuracrImage(
	neuracrImageProperty: NeuracrImageProperty,
	maxImageHeight: Dp,
	modifier: Modifier = Modifier,
) {
	val imageModifier = modifier
		.clip(shape = MaterialTheme.shapes.medium)
		.heightIn(max = maxImageHeight)
	when (neuracrImageProperty) {
		is NeuracrImageProperty.Resource -> Image(
			painter = painterResource(neuracrImageProperty.imageRes),
			contentDescription = neuracrImageProperty.contentDescription,
			contentScale = ContentScale.FillWidth,
			modifier = imageModifier,
		)
		is NeuracrImageProperty.Remote -> {
			var isImageLoading by remember { mutableStateOf(true) }
			if (isImageLoading) {
				NeuracrShimmer(modifier.height(maxImageHeight))
			}
			AsyncImage(
				model = neuracrImageProperty.url,
				contentDescription = neuracrImageProperty.contentDescription,
				contentScale = ContentScale.FillWidth,
				onSuccess = {
					isImageLoading = false
				},
				modifier = imageModifier,
			)
		}
		is NeuracrImageProperty.None -> {}
	}
}

@Composable
@Preview(showBackground = true)
private fun ResourceNeuracrImagePreview() {
	NeuracrTheme {
		NeuracrImage(
			NeuracrImageProperty.Resource(
				contentDescription = null,
				imageRes = R.drawable.bretzel
			), 120.dp
		)
	}
}

@Composable
@Preview(showBackground = true)
private fun RemoteNeuracrImagePreview() {
	NeuracrTheme {
		NeuracrImage(
			NeuracrImageProperty.Remote(
				contentDescription = null,
				url = "https://neuracr.github.io/recipes/2022/03/06/bretzels.html"
			), 120.dp
		)
	}
}
