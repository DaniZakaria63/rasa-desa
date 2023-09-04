package com.shapeide.rasadesa.search.domain

import androidx.appsearch.annotation.Document
import androidx.appsearch.app.AppSearchSchema

@Document
data class MealSearch (
    @Document.Namespace
    val namespace: String= "meal",

    @Document.Id
    val id: String,

    @Document.StringProperty(
        indexingType = AppSearchSchema.StringPropertyConfig.INDEXING_TYPE_PREFIXES
    )
    val text: String
)