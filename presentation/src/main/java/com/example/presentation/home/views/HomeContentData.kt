package com.example.presentation.home.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.flags.NeuracrFlagProperty
import com.example.design_system.image.NeuracrImageProperty
import com.example.design_system.theming.NeuracrTheme
import com.example.presentation.R
import com.example.presentation.home.models.HomeRecipeUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContentData(
	homeRecipeUiModels: List<HomeRecipeUiModel>,
	homeRecipeClick: (HomeRecipeUiModel) -> Unit,
	modifier: Modifier = Modifier) {
	val lazyGridState = rememberLazyStaggeredGridState()
	LazyVerticalStaggeredGrid(
		columns = StaggeredGridCells.Adaptive(300.dp),
		contentPadding = PaddingValues(32.dp),
		verticalItemSpacing = 16.dp,
		horizontalArrangement = Arrangement.spacedBy(16.dp),
		state = lazyGridState,
		modifier = modifier.fillMaxSize()
	) {
		item(span = StaggeredGridItemSpan.FullLine) {
			Text(
				text = stringResource(id = R.string.home_title),
				style = MaterialTheme.typography.displaySmall,
				color = MaterialTheme.colorScheme.onBackground,
			)
		}
		items(homeRecipeUiModels) { homeRecipeUiModel ->
			HomeRecipeCard(
				homeRecipeUiModel = homeRecipeUiModel,
				modifier = Modifier
					.fillMaxWidth()
					.clickable {
						homeRecipeClick(homeRecipeUiModel)
					}
			)
		}
	}
}

@Composable
@Preview(showBackground = true)
private fun HomeContentDataPreview() {
	NeuracrTheme {
		HomeContentData(
			homeRecipeUiModels = List(6) {
				HomeRecipeUiModel(
					id = "",
					title = "bretzels",
					imageProperty = NeuracrImageProperty.Resource(
						contentDescription = null,
						imageRes = com.example.design_system.R.drawable.bretzel
					),
					flagProperty = NeuracrFlagProperty.FRENCH,
				)
			},
			{ }
		)
	}
}
