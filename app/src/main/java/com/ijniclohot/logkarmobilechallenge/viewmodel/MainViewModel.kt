package com.ijniclohot.logkarmobilechallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import com.ijniclohot.logkarmobilechallenge.repository.LocalRepository
import com.ijniclohot.logkarmobilechallenge.utils.FormFillType

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val localRepository = LocalRepository.getInstance(application)

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

    fun saveOrder(orderModel: OrderModel) {
        localRepository.saveOrder(orderModel)
    }


}