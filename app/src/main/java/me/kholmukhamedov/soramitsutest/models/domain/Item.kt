package me.kholmukhamedov.soramitsutest.models.domain

import java.io.Serializable

/**
 * Domain layer entity
 */
data class Item(var title: String,
                var imageUrl: String) : Serializable
