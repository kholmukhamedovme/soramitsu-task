package me.kholmukhamedov.soramitsutest.models.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Data layer bean
 */
data class FeedBean(@JsonProperty("title") var title: String,
                    @JsonProperty("link") var link: String,
                    @JsonProperty("description") var description: String,
                    @JsonProperty("modified") var modified: String,
                    @JsonProperty("generator") var generator: String,
                    @JsonProperty("items") var items: List<Item>) : Serializable {

    /**
     * Sub-bean
     */
    data class Item(@JsonProperty("title") var title: String,
                    @JsonProperty("link") var link: String,
                    @JsonProperty("media") var media: Media,
                    @JsonProperty("date_taken") var dateTaken: String,
                    @JsonProperty("description") var description: String,
                    @JsonProperty("published") var published: String,
                    @JsonProperty("author") var author: String,
                    @JsonProperty("author_id") var authorId: String,
                    @JsonProperty("tags") var tags: String) : Serializable {

        /**
         * Sub-sub-bean
         */
        data class Media(@JsonProperty("m") var m: String) : Serializable

    }

}
