package com.xnjz.one.room2

import androidx.room.Embedded
import androidx.room.Entity

/**
 */
@Entity
data class ReadHistory(
    @Embedded
    var article: Article,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "articleId",
//    )
//    var tags: MutableList<Tag>
)