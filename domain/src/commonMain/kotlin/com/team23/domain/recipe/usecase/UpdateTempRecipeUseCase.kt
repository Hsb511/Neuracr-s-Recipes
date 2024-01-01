package com.team23.domain.recipe.usecase

import com.team23.domain.recipe.model.RecipeDomainModel
import com.team23.domain.recipe.repository.RecipeRepository

class UpdateTempRecipeUseCase(
    private val createNewRecipeUseCase: CreateNewRecipeUseCase,
    private val recipeRepository: RecipeRepository,
) {
    suspend fun invoke(recipe: RecipeDomainModel.Full) {
        if (recipe != createNewRecipeUseCase.invoke()) {
            recipeRepository.updateRecipe(recipe)
        }
    }
}
