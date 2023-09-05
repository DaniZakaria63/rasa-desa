package com.shapeide.rasadesa.remote.mappers

fun ArrayList<String>.listToString(): String {
    return joinToString(", ")
}