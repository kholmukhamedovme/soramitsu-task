package me.kholmukhamedov.soramitsutest.models.presentation

import java.io.Serializable

/**
 * Presentation layer model
 */
data class ItemModel(var title: String,
                     var imageUrl: String) : Serializable {

    fun getFullscreenImageUrl() = imageUrl.replace("_m", "")

}
