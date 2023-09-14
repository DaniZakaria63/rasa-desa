package com.shapeide.rasadesa.presenter

import org.junit.Test

import org.junit.Assert.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun check(){
        val nomer = Nomer("satu", "dua")
        val propertyNames = nomer::class.primaryConstructor!!.parameters.map { it.name }
        assertEquals(propertyNames, listOf("one","two"))

        val nomerValue = nomer::class.memberProperties.first{it.name == propertyNames[0]}
        assertEquals(nomerValue.getter.call(nomer), nomer.one)
    }

    @Test
    fun zipCheck(){
        val list1 = listOf<String>("satu", "dua")
        val list2 = listOf<String>("one", "two")
        list1.zip(list2).forEachIndexed{index, (satu, dua)->
            assertEquals(satu, list1[index])
            assertEquals(dua, list2[index])
        }
    }
}

data class Nomer(
    val one: String,
    val two: String,
)