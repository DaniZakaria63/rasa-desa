package com.shapeide.rasadesa.utills

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.recyclerview.widget.DiffUtil
import com.shapeide.rasadesa.domains.Area
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.domains.Ingredient

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

/*
 * Truncate long text with a preference for word boundaries and without trailing punctuation.
 */
fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}

/*
    isOnline() function will check the network state inside repository files,
    it is different from the NetworkStateListener that listen real-time as ViewModel.
 */
fun isOnline(context: Context) : Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if(cm == null) return false
    val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
    if(cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return true
    if(cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return true
    if(cap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return true
    return false
}

/*  ---- DONT REPEAT YOUR SELF!!!
    *but i still haven't get enlighten from this suffering yet
 */

class CategoryDiffCallback(
    val oldValue: ArrayList<Category>,
    val newValue: ArrayList<Category>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldValue.size

    override fun getNewListSize(): Int = newValue.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).id == newValue.get(newItemPosition).id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).name.equals(newValue.get(newItemPosition).name)
}

class FilterMealDiffCallback(
    val oldValue: ArrayList<FilterMeal>,
    val newValue: ArrayList<FilterMeal>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldValue.size

    override fun getNewListSize(): Int = newValue.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).id == newValue.get(newItemPosition).id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).name.equals(newValue.get(newItemPosition).name)
}

class AreaDiffCallback(
    val oldValue: ArrayList<Area>,
    val newValue: ArrayList<Area>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldValue.size

    override fun getNewListSize(): Int = newValue.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).strArea == newValue.get(newItemPosition).strArea

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).strArea.equals(newValue.get(newItemPosition).strArea)
}

class IngredientDiffCallback(
    val oldValue: ArrayList<Ingredient>,
    val newValue: ArrayList<Ingredient>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldValue.size

    override fun getNewListSize(): Int = newValue.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).idIngredient == newValue.get(newItemPosition).idIngredient

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldValue.get(oldItemPosition).idIngredient.equals(newValue.get(newItemPosition).idIngredient)
}
