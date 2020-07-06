package com.ijniclohot.logkarmobilechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijniclohot.logkarmobilechallenge.utils.FormFillType

class MainViewModel : ViewModel() {
    private val _tabValidation by lazy {
        MutableLiveData<List<FormFillType>>()
    }

    val tabValidation: LiveData<List<FormFillType>>
        get() = _tabValidation

    private val initialValidation =
        listOf(FormFillType.INVALID, FormFillType.INVALID, FormFillType.INVALID)

    init {
        _tabValidation.value = initialValidation
    }

    fun setValidation(formFillType: FormFillType, index: Int) {
        (initialValidation as MutableList)[index] = formFillType
        _tabValidation.value = initialValidation
    }


}