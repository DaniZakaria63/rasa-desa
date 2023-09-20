package com.shapeide.rasadesa.presenter.base

import com.shapeide.rasadesa.domain.domain.Nutrients
import com.shapeide.rasadesa.domain.domain.NutrientsSub
import com.shapeide.rasadesa.domain.domain.Recipe
import com.shapeide.rasadesa.presenter.base.source.BaseDataState
import com.shapeide.rasadesa.presenter.base.source.StateError
import com.shapeide.rasadesa.presenter.domain.Status
import timber.log.Timber
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

data class RecipeDataState(
    var recipe: Recipe? = null,
    override val isError: Boolean = false,
    override val isLoading: Boolean = false,
    override val eventError: StateError? = StateError(message = "")
): BaseDataState(isLoading, isError, eventError) {
    override val status: Status
        get() = super.status

    private val nutrientsFields: List<String> = Nutrients::class.primaryConstructor!!.parameters.map { it.name?:"-" }

    fun extractValueList(nutrients: Nutrients): List<NutrientsSub>{
        val list :MutableList<NutrientsSub> = mutableListOf()
        try {
            nutrientsFields.forEach { nutrient ->
                val value = nutrients::class.memberProperties.first { it.name == nutrient }
                list.add(value.getter.call(nutrients) as NutrientsSub)
            }
        }catch(e: Exception){
            Timber.d("Error Nutrient Key List: $nutrientsFields => $nutrients")
            Timber.e(e)
        }
        return list
    }

}