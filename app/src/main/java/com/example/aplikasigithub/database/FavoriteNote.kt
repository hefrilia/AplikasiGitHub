package com.example.aplikasigithub.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteNote (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "user")
    var namaUser: String? = null,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null

) : Parcelable