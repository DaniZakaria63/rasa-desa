package com.shapeide.rasadesa.presenter.domain

data class AboutContentHolder(
    val name: String,
    val value: String,
    val type: AboutType,
    val typeCallback: String? = ""
){
    enum class AboutType{
        Link,
        Text
    }
}
