package com.monkey.pagingtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class PagingViewModel (
    private val repository: PagingRepository
) : ViewModel(){
    val pagingData = repository.getPagingData()
                    .cachedIn(viewModelScope)   //캐싱


    //팩토리 클래스 (이거 안 하고 그냥 by viewModels로 하면 빈 생성자 없다는 exception 뜸)
    class Factory(private val repository: PagingRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PagingViewModel(repository) as T
        }
    }
}