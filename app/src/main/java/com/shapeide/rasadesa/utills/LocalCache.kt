package com.shapeide.rasadesa.utills

import com.shapeide.rasadesa.networks.models.CacheModel

class LocalCache {
    private val cache : HashMap<String, CacheModel> = HashMap()

    fun cacheEvent(eventId : String, eventData : String){
        val cacheItem = CacheModel(eventId, eventData)
        cache[eventId] = cacheItem
    }

    fun getEvent(eventId: String) : String{
        val cacheItem = cache[eventId]
        return cacheItem?.eventValue.toString()
    }

    fun clearCache(){
        cache.clear()
    }
}