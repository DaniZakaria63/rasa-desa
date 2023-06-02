package com.shapeide.rasadesa.utills

import androidx.recyclerview.widget.DiffUtil
import com.shapeide.rasadesa.domains.Category

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

/**
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
