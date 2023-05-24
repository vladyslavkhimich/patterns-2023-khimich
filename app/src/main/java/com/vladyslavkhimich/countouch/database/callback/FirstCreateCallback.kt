package com.vladyslavkhimich.countouch.database.callback

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vladyslavkhimich.countouch.database.dao.TagDao
import com.vladyslavkhimich.countouch.database.entity.Tag
import com.vladyslavkhimich.countouch.model.dto.TagDto
import com.vladyslavkhimich.countouch.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider

class FirstCreateCallback(
    @ApplicationContext
    private val applicationContext: Context,
    private val provider: Provider<TagDao>
): RoomDatabase.Callback() {

    private val appScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        appScope.launch(Dispatchers.IO) {
            prepopulateTags()
        }
    }

    private suspend fun prepopulateTags() {
        val jsonString = applicationContext.assets.open(Constants.TAGS_FILE).bufferedReader().use { it.readText() }
        val tagsListType = object: TypeToken<List<TagDto>>() {}.type
        val tags = Gson().fromJson<List<TagDto>>(jsonString, tagsListType)
        val dbTags = tags.map {
            Tag(name = it.name)
        }
        provider.get().insertAll(dbTags)
    }
}