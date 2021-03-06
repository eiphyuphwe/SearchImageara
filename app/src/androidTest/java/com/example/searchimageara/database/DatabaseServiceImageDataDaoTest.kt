package com.example.searchimageara.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.domain.model.Provider
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
class DatabaseServiceImageDataDaoTest {

    @get:Rule
    var hitRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: DatabaseService

    private lateinit var imageDataDao: ImageDataDao

    @Before
    fun setUp() {

        hitRule.inject()
        imageDataDao = db.imageDao()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun testImageDao_selectAllImageDataWithoutSaving_ReturnEmpty() = runBlocking {
        assertEquals(0, imageDataDao.selectAllData().size)
    }

    @Test
    fun testImageDao_saveAllImageData_Success() = runBlocking {
        val list = generateData("cats")
        imageDataDao.saveAllImageData(list)
        assertNotNull(imageDataDao.selectAllData())
        assertEquals(3, imageDataDao.selectAllData().size)
    }

    @Test
    fun testImageDao_saveAndDeleteAndSelectAllImageData_Success() = runBlocking {
        val list = generateData("cats")
        imageDataDao.saveAllImageData(list)

        val list1 = generateData("dogs")
        imageDataDao.saveAllImageData(list1)
        assertNotNull(imageDataDao.selectAllData())
        assertEquals(6, imageDataDao.selectAllData().size)

        imageDataDao.deleteImageDataByQuery("dogs")
        assertEquals(3, imageDataDao.selectAllData().size)
    }

    private fun generateData(query: String): List<ImageData> {
        val list = mutableListOf<ImageData>()
        for (i in 1..3) {
            val imageData = ImageData(
                "url $query$i", 100, 100, "thumbnail", 40, 40, "",
                "name", "title", Provider(), "imageWebSearchUrl", "searchUrl", query
            )
            list.add(imageData)
        }
        return list
    }

}