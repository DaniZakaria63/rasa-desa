package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName


data class IngredientsModel (

  @SerializedName("text"         ) var text         : String? = null,
  @SerializedName("quantity"     ) var quantity     : Int?    = null,
  @SerializedName("measure"      ) var measure      : String? = null,
  @SerializedName("food"         ) var food         : String? = null,
  @SerializedName("weight"       ) var weight       : Int?    = null,
  @SerializedName("foodCategory" ) var foodCategory : String? = null,
  @SerializedName("foodId"       ) var foodId       : String? = null,
  @SerializedName("image"        ) var image        : String? = null

)