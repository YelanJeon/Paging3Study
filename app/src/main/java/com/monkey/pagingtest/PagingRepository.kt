package com.monkey.pagingtest

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class PagingRepository(
    private val service: SampleBackendService
) {
    fun getPagingData() : Flow<PagingData<String>> {
        return Pager(PagingConfig(pageSize = 10)) {    //서버에서 불러올 아이템 갯수
            PagingSource(service)
        }.flow
    }
}