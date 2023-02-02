package com.shapeide.rasadesa.networks

class NetworkRepository {
    fun getRandomMeal() = APIEndpoint.create().getRandomMeal()
}