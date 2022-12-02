package com.monkey.pagingtest

import kotlinx.coroutines.delay

class SampleBackendService {
    /**
     * 서버에서 데이터를 불러오는 클래스
     * 여기서는 단순히 for문으로 데이터를 만들어서 반환함
     * 한 번에 열 개의 데이터를 반환하게 됨
     */

    suspend fun getPagingData(page: Int) : PagingSample {
        //임의의 시간동안 데이터를 로딩하도록 한다
        delay(3000)

        val result = mutableListOf<String>()
        val start = page * 10
        for(item in start until start + 10) {
            result.add("$item")
        }

        return PagingSample(
            data = result,
            page = page + 1
        )
    }
}