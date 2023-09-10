package com.shapeide.rasadesa.presenter.mappers

import com.shapeide.rasadesa.presenter.base.RecipeDataState
import com.shapeide.rasadesa.presenter.detail.state.DetailScreenState


fun RecipeDataState.toState(): DetailScreenState = DetailScreenState(
    header = DetailScreenState.Header(
        calories = recipe?.calories.toString(),
        time = recipe?.totalTime.toString(),
        weight = recipe?.totalWeight.toString(),
        source = recipe?.source.toString(),
        sourceLink = recipe?.url.toString(),
        image = recipe?.image.toString()
    ),
    others = DetailScreenState.Others(
//        emission = recipe.emission,
//        emissionClass = recipe.emissionClass,
//        cuisineType = recipe.cuisineType,
//        mealType = recipe.mealType,
        healthLabels = recipe?.healthLabels
    ),
    ingredients = recipe?.ingredients,
    nutrients = recipe?.totalNutrients,
    dailyNutrients = recipe?.totalDaily,
    digest = recipe?.digest,
)