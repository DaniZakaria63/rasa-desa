package com.shapeide.rasadesa.networks

import com.shapeide.rasadesa.domain.Category
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.utills.Resource
import com.shapeide.rasadesa.viewmodel.MainActivityVM
import retrofit2.Response

/**
 * this class should be given a clean result which depend as their function name
 * this one belongs to local/repo/{entityrepo}
 */

/* This Class Is No Need To Be Used */
class NetworkRepository {
    private var mAPIEndpoint: APIEndpoint = APIEndpoint.create()

    fun getRandomMeal() = mAPIEndpoint.getRandomMeal()
    suspend fun getCategories(): List<Category>? {
        val response = mAPIEndpoint.getCategories().asDomainModel()
//        val parsedResponse =
//            handleResponse(
//                MainActivityVM.TYPE_CATEGORIES,
//                response.awaitResponse()
//            ) as Resource<List<CategoryModel>>
        return response
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> handleResponse(type: String, response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            if (type.equals(MainActivityVM.TYPE_MEALS)) {
                val parsedResponse = response as Response<ResponseMeals<T>>
                parsedResponse.body()?.meals.let { data ->
                    return Resource.Success(data!!) as Resource<T>
                }
            } else if (type.equals(MainActivityVM.TYPE_CATEGORIES)) {
                val parsedResponse = response as Response<ResponseCategory<T>>
                parsedResponse.body()?.categories.let { data ->
                    return Resource.Success(data!!) as Resource<T>
                }
            } else {
                throw IllegalArgumentException("Kategori tidak ada")
            }
        }
        return Resource.Error(response.message())
    }
}