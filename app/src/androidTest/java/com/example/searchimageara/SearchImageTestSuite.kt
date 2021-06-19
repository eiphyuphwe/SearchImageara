package com.example.searchimageara

import androidx.paging.ExperimentalPagingApi
import com.example.searchimageara.database.DatabaseServiceImageDataDaoTest
import com.example.searchimageara.database.DatabaseServiceRemoteKeysDaoTest
import com.example.searchimageara.reposistory.SearchImageRemoteMediatorTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalPagingApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    DatabaseServiceImageDataDaoTest::class,
    DatabaseServiceRemoteKeysDaoTest::class,
    SearchImageRemoteMediatorTest::class,

)

class SearchImageTestSuite