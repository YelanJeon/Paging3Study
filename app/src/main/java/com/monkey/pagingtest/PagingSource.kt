package com.monkey.pagingtest

import androidx.paging.PagingState

class PagingSource (
    private val service: SampleBackendService
        ) : androidx.paging.PagingSource<Int, String>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val currentPage = params.key ?: 0
            val response = service.getPagingData(currentPage)  //서버로부터 데이터를 받아온다.

            //성공한 경우 LoadResult.Page 반환
            LoadResult.Page(
                data = response.data,
                prevKey = if(currentPage == 0) null else currentPage - 1,
                nextKey = currentPage + 1
            )
        }catch(e: java.lang.Exception) {
            //실패한 경우 LoadResult.Error 반환
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}