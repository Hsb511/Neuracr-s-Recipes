package com.example.neuracrsrecipes.navigation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.design_system.scaffold.NavItemProperty
import com.example.design_system.scaffold.NeuracrScaffold
import com.example.presentation.drawer.ModalMenuDrawer
import com.example.presentation.home.HomeScreen
import com.example.presentation.recipe.RecipeScreen
import com.example.presentation.recipe.extensions.toCleanRecipeId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NavHost(context: Context) {
	val navController = rememberNavController()
	val navItems = toNavItemProperties(listOf(AppPage.Home, AppPage.Search, AppPage.About), context, navController)
	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	val scope = rememberCoroutineScope()
	NeuracrScaffold(
		navItemProperties = navItems,
		navigateUp = { navController.navigateUp() },
		isNavigationEmpty = navController.previousBackStackEntry == null,
		drawerState = drawerState,
		openMenu = {
			scope.launch(Dispatchers.IO) {
				drawerState.open()
			}
		},
		closeMenu = {
			scope.launch(Dispatchers.IO) {
				drawerState.close()
			}
		}
	) { padding ->
		ModalMenuDrawer(drawerState, Modifier.padding(padding)) {
			NavHost(navController = navController, startDestination = AppPage.Home.route) {
				composable(route = AppPage.Home.route) {
					HomeScreen(
						homeRecipeClick = { homeRecipeUiModel ->
							navController.navigate("${AppPage.WithArgument.Recipe.route}/${homeRecipeUiModel.id.toCleanRecipeId()}") // TODO ID
						}
					)
				}
				composable(
					route = "${AppPage.WithArgument.Recipe.route}/{${AppPage.WithArgument.Recipe.argumentName}}",
					arguments = listOf(navArgument(AppPage.WithArgument.Recipe.argumentName) {
						type = NavType.StringType
					})
				) { navBackStackEntry ->
					RecipeScreen(
						cleanRecipeId = navBackStackEntry.arguments?.getString(AppPage.WithArgument.Recipe.argumentName)
					)
				}
				composable(route = AppPage.Search.route) {

				}
				composable(route = AppPage.About.route) {

				}
			}
		}
	}
}

@Composable
internal fun toNavItemProperties(
	appPages: List<AppPage>,
	context: Context,
	navController: NavHostController
): List<NavItemProperty> =
	appPages.map { appPage ->
		val currentScreenRoute = navController.currentBackStackEntryAsState().value?.destination?.route
			?: AppPage.Home.route
		val isSelected = currentScreenRoute == appPage.route ||
			(appPage == AppPage.Home && currentScreenRoute.contains(AppPage.Home.route))
		val icon = when (appPage) {
			AppPage.Home -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
			AppPage.Search -> if (isSelected) Icons.Filled.Search else Icons.Outlined.Search
			AppPage.About -> if (isSelected) Icons.Filled.Info else Icons.Outlined.Info
			else -> Icons.Filled.Done
		}
		NavItemProperty(
			title = context.getString(appPage.displayNameId),
			icon = icon,
			isSelected = isSelected,
			onNavigate = { navController.navigate(appPage.route) },
		)
	}
