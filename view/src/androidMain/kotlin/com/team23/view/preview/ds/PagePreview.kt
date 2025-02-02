package com.team23.view.preview.ds

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.team23.neuracrsrecipes.model.uimodel.ErrorUiModel
import com.team23.view.navigation.screen.ErrorScreen
import com.team23.view.navigation.screen.InProgressScreen
import com.team23.view.theme.PopoteTheme

@Composable
@Preview(showSystemUi = true)
fun InProgressScreenPreview() {
    PopoteTheme {
        InProgressScreen()
    }
}

@Composable
@Preview(showSystemUi = true)
fun ErrorScreenPreview() {
    PopoteTheme {
        ErrorScreen(errorUiModel = ErrorUiModel("An error occured") {})
    }
}
