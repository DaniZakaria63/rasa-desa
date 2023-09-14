package com.shapeide.rasadesa.presenter.search.state

data class SearchScreenState(
    val queryHolder: String? = "",
    val errorEvent: SearchError? = SearchError()
) {
    data class SearchError(
        var throwable: Throwable? = null
    ){
        val message: String? get() = throwable?.message
    }
}