package com.xnjz.one.room2

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by admin on 2019-09-18.
 */
@Keep
@Entity
data class Article(
    var apkLink: String? = "",
    var audit: Int = 0,
    var author: String? = "",
    var chapterId: Int = 0,
    var chapterName: String? = "",
    var collect: Boolean = false,
    var courseId: Int = 0,
    var desc: String? = "",
    var envelopePic: String? = "",
    var fresh: Boolean = false,
    @PrimaryKey
    var id: Long = 0,
    var link: String? = "",
    var niceDate: String? = "",
    var niceShareDate: String? = "",
    var origin: String? = "",
    var originId: Long = 0,
    var prefix: String? = "",
    var projectLink: String? = "",
    var publishTime: Long = 0,
    var selfVisible: Int = 0,
    var shareDate: Long? = 0,
    var shareUser: String? = "",
    var superChapterId: Int = 0,
    var superChapterName: String? = "",
    var title: String? = "",
    var type: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0,
    var top: Boolean = false,
    // 历史记录sqlite数据库专用字段，阅读时间
    var readTime: Long = 0L
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(apkLink)
        parcel.writeInt(audit)
        parcel.writeString(author)
        parcel.writeInt(chapterId)
        parcel.writeString(chapterName)
        parcel.writeByte(if (collect) 1 else 0)
        parcel.writeInt(courseId)
        parcel.writeString(desc)
        parcel.writeString(envelopePic)
        parcel.writeByte(if (fresh) 1 else 0)
        parcel.writeLong(id)
        parcel.writeString(link)
        parcel.writeString(niceDate)
        parcel.writeString(niceShareDate)
        parcel.writeString(origin)
        parcel.writeLong(originId)
        parcel.writeString(prefix)
        parcel.writeString(projectLink)
        parcel.writeLong(publishTime)
        parcel.writeInt(selfVisible)
        parcel.writeValue(shareDate)
        parcel.writeString(shareUser)
        parcel.writeInt(superChapterId)
        parcel.writeString(superChapterName)
        parcel.writeString(title)
        parcel.writeInt(type)
        parcel.writeInt(userId)
        parcel.writeInt(visible)
        parcel.writeInt(zan)
        parcel.writeByte(if (top) 1 else 0)
        parcel.writeLong(readTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}