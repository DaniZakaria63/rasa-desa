package com.shapeide.rasadesa.presenter.mappers

import com.shapeide.rasadesa.domain.domain.Nutrients
import com.shapeide.rasadesa.presenter.base.RecipeDataState
import com.shapeide.rasadesa.presenter.detail.state.DetailScreenState


fun RecipeDataState.toState(): DetailScreenState = DetailScreenState(
    header = DetailScreenState.Header(
        title = recipe?.label.toString(),
        calories = recipe?.calories?.toInt().toString(),
        time = recipe?.totalTime.toString(),
        weight = recipe?.totalWeight?.toInt().toString(),
        source = recipe?.source.toString(),
        sourceLink = recipe?.url.toString(),
        image = recipe?.image.toString()
    ),
    others = DetailScreenState.Others(
//        emission = recipe.emission,
//        emissionClass = recipe.emissionClass,
//        cuisineType = recipe.cuisineType,
//        mealType = recipe.,
        healthLabels = recipe?.healthLabels
    ),
    ingredients = recipe?.ingredients,
    nutrients = extractValueList(recipe?.totalNutrients?: Nutrients()),
    dailyNutrients = recipe?.totalDaily,
    digest = recipe?.digest,
)