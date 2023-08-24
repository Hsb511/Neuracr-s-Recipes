package com.team23.neuracrsrecipes.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
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
import com.team23.design_system.scaffold.NavItemProperty
import com.team23.design_system.scaffold.NeuracrScaffold
import com.team23.neuracrsrecipes.BuildConfig
import com.team23.presentation.add.AddScreen
import com.team23.presentation.common.handlers.AppPage
import com.team23.presentation.common.handlers.NavigationHandler
import com.team23.presentation.drawer.ModalMenuDrawer
import com.team23.presentation.drawer.models.DrawerUiModel
import com.team23.presentation.favorite.FavoriteScreen
import com.team23.presentation.home.HomeScreen
import com.team23.presentation.recipe.RecipeScreen
import com.team23.presentation.search.SearchScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NavHost(context: Context) {
	val navController = rememberNavController()
	val navigationHandler = NavigationHandler(navController)
	val snackbarHostState = remember { SnackbarHostState() }
	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	val scope = rememberCoroutineScope()
	val scrollState = rememberScrollState()
	val heightToBeFaded = remember { mutableStateOf(0f) }
	val title: MutableState<String?> = remember { mutableStateOf(null) }
	val navItems = toNavItemProperties(
		listOf(AppPage.Home, AppPage.Search, AppPage.Upload, AppPage.Favorite),
		context,
		navController,
		scope,
		drawerState
	)
	NeuracrScaffold(
		snackbarHostState = snackbarHostState,
		scrollState = scrollState,
		heightToBeFaded = heightToBeFaded.value,
		title = title.value,
		navItemProperties = navItems,
		navigateUp = {
			navController.navigateUp()
			scope.launch(Dispatchers.IO) { drawerState.close() }
		},
		isNavigationEmpty = navController.previousBackStackEntry == null,
		drawerState = drawerState,
		openMenu = { scope.launch(Dispatchers.IO) { drawerState.open() } },
		closeMenu = { scope.launch(Dispatchers.IO) { drawerState.close() } },
	) { padding ->
		ModalMenuDrawer(DrawerUiModel(drawerState, BuildConfig.VERSION_NAME), Modifier.padding(padding)) {
			NavHost(navController = navController, startDestination = AppPage.Home.route) {
				composable(route = AppPage.Home.route) {
					HomeScreen(
						snackbarHostState = snackbarHostState,
						onRecipeClick = { homeRecipeUiModel -> navigationHandler.openRecipe(homeRecipeUiModel.id) },
					)
					title.value = null
				}
				composable(
					route = "${AppPage.WithArgument.Recipe.route}/{${AppPage.WithArgument.Recipe.argumentName}}",
					arguments = listOf(navArgument(AppPage.WithArgument.Recipe.argumentName) {
						type = NavType.StringType
					})
				) { navBackStackEntry ->
					RecipeScreen(
						scrollState = scrollState,
						snackbarHostState = snackbarHostState,
						heightToBeFaded = heightToBeFaded,
						title = title,
						cleanRecipeId = navBackStackEntry.arguments?.getString(AppPage.WithArgument.Recipe.argumentName),
						onTagClicked = { tag -> navigationHandler.openSearch(tag) }
					)
					LaunchedEffect(scrollState) {
						scrollState.animateScrollTo(0)
					}
				}
				composable(route = AppPage.Search.route) {
					SearchScreen(
						snackbarHostState = snackbarHostState,
						onRecipeClick = { recipeUiModel -> navigationHandler.openRecipe(recipeUiModel.id) }
					)
					title.value = null
				}
				composable(route = "${AppPage.Search.route}/{${AppPage.WithArgument.Search.argumentName}}") { navBackStackEntry ->
					SearchScreen(
						snackbarHostState = snackbarHostState,
						onRecipeClick = { recipeUiModel -> navigationHandler.openRecipe(recipeUiModel.id) },
						selectedTag = navBackStackEntry.arguments?.getString(AppPage.WithArgument.Search.argumentName),
					)
					title.value = null
				}
				composable(route = AppPage.Upload.route) {
					AddScreen(
						scrollState = scrollState,
						heightToBeFaded = heightToBeFaded,
						snackbarHostState = snackbarHostState,
					)
				}

				composable(route = AppPage.Favorite.route) {
					FavoriteScreen(
						onRecipeClick = { recipeUiModel -> navigationHandler.openRecipe(recipeUiModel.id) },
					)
					title.value = null
				}
			}
		}
	}
}

@Composable
internal fun toNavItemProperties(
	appPages: List<AppPage>,
	context: Context,
	navController: NavHostController,
	scope: CoroutineScope,
	drawerState: DrawerState,
): List<NavItemProperty> =
	appPages.map { appPage ->
		val currentScreenRoute = navController.currentBackStackEntryAsState().value?.destination?.route
			?: AppPage.Home.route
		val isSelected = currentScreenRoute == appPage.route ||
			(appPage == AppPage.Home && currentScreenRoute.contains(AppPage.Home.route)) ||
			(appPage == AppPage.Search && currentScreenRoute.contains(AppPage.Search.route))
		val icon = when (appPage) {
			AppPage.Home -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
			AppPage.Search -> if (isSelected) Icons.Filled.Search else Icons.Outlined.Search
			AppPage.Upload -> if (isSelected) Icons.Filled.AddCircle else Icons.Outlined.Add
			AppPage.Favorite -> if (isSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
			else -> Icons.Filled.Done
		}
		NavItemProperty(
			title = context.getString(appPage.displayNameId),
			icon = icon,
			isSelected = isSelected,
			onNavigate = {
				navController.navigate(appPage.route)
				scope.launch(Dispatchers.IO) { drawerState.close() }
			},
		)
	}
