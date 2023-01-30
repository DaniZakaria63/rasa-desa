package com.shapeide.rasadesa.networks

import com.shapeide.rasadesa.models.MealModel
import retrofit2.Response

class APIHelper(private val apiService: APIEndpoint) : APIBaseDataParse() {
    private val CALL_TYPE_GET = "get"

    private suspend fun <T> getAPICall(
        type: String,
        path: String,
        body: HashMap<String, Any>
    ): Response<T> {
        return when(type){
            CALL_TYPE_GET -> apiService.getRequest(path, body as HashMap<String, String>)
            else -> apiService.getRequest(path, body as HashMap<String, String>)
        }
    }

    suspend fun callGetRandomMeal() = getResultAPI(type = ResponseMeals::class.java){
        val body = HashMap<String, String>()
        getAPICall(CALL_TYPE_GET, "${APIEndpoint.BASE_URL}/api/json/v1/1/random.php", body = body as HashMap<String, Any>)
    }
}