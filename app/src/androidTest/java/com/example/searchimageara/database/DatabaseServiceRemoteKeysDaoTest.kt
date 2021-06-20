package com.example.searchimageara.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.searchimageara.database.DatabaseService
import com.example.searchimageara.domain.model.ImageDataKeys
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class DatabaseServiceRemoteKeysDaoTest {

    @get:Rule
    var hitRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    @Named("test_db")
    lateinit var db: DatabaseService
    lateinit var remoteKeyDao: RemoteKeyDao

    @Before
    fun setUp() {
        hitRule.inject()
        remoteKeyDao = db.remoteKeyDao()
    }

    @After
   fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun testRemoteKeysDao_selectAllRemoteKeysWithoutSaving_ReturnEmpty() = runBlocking {
        assertEquals(0, remoteKeyDao.selectAllKeys()?.size)
    }

    @Test
    fun testRemoteKeysDao_saveAllRemoteKeys_Success() = runBlocking {
        val key = ImageDataKeys(1, 1, 10, "cats", 10)
        remoteKeyDao.insertOrReplaceRemoteKeys(key)
        assertNotNull(remoteKeyDao.selectAllKeys())
        assertEquals(1, remoteKeyDao.selectAllKeys().size)
    }

    @Test
    fun testRemoteKeysDao_saveAndDeleteAndSelectAllRemoteKeys_Success() = runBlocking {
        val key = ImageDataKeys(1, 1, 10, "cats", 10)
        remoteKeyDao.insertOrReplaceRemoteKeys(key)

        val key1 = ImageDataKeys(2, 1, 10, "dogs", 10)
        remoteKeyDao.insertOrReplaceRemoteKeys(key1)
        assertNotNull(remoteKeyDao.selectAllKeys())
        assertEquals(2, remoteKeyDao.selectAllKeys().size)

        remoteKeyDao.deleteByQuery("dogs")
        assertEquals(1, remoteKeyDao.selectAllKeys().size)
    }
}