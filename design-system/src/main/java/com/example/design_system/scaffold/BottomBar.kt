package com.example.design_system.scaffold

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theming.NeuracrTheme

@Composable
internal fun BottomBar(navItemProperties: List<NavItemProperty>) {
	NavigationBar(
		containerColor = MaterialTheme.colorScheme.primary,
		contentColor = MaterialTheme.colorScheme.onPrimary,
		modifier = Modifier.height(56.dp)
	) {
		navItemProperties.map { navItemProperty ->
			NavigationBarItem(
				selected = navItemProperty.isSelected,
				onClick = navItemProperty.onNavigate,
				icon = {
					Icon(
						imageVector = navItemProperty.icon,
						contentDescription = null,
					)
				},
				label = {
					Text(
						text = navItemProperty.title,
						fontWeight = if (navItemProperty.isSelected) FontWeight.ExtraBold else null,
						modifier = Modifier.offset(y = 22.dp)
					)
				},
				colors = NavigationBarItemDefaults.colors(
					selectedIconColor = MaterialTheme.colorScheme.scrim,
					selectedTextColor = MaterialTheme.colorScheme.scrim,
					indicatorColor = MaterialTheme.colorScheme.surface,
					unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
					unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
				),
				modifier = Modifier.offset(y = (-8).dp)
			)
		}
	}
}

@Composable
@Preview(showBackground = true)
private fun BottomBarPreview() {
	NeuracrTheme {
		BottomBar(
			listOf(
				NavItemProperty("Home", Icons.Filled.Home, true) {},
				NavItemProperty("Search", Icons.Filled.Search, false) {},
				NavItemProperty("About", Icons.Filled.Info, false) {},
			)
		)
	}
}

data class NavItemProperty(
	val title: String,
	val icon: ImageVector,
	val isSelected: Boolean,
	val onNavigate: () -> Unit
)
