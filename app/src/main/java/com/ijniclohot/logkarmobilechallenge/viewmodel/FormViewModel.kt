package com.ijniclohot.logkarmobilechallenge.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijniclohot.logkarmobilechallenge.models.DestinationModel
import com.ijniclohot.logkarmobilechallenge.models.OriginModel
import com.ijniclohot.logkarmobilechallenge.repository.ApiRepository
import com.ijniclohot.logkarmobilechallenge.utils.FormFillType

class FormViewModel : ViewModel() {

    companion object {
        const val TAG = "FormViewModel"
    }

    private val apiRepository by lazy {
        ApiRepository.getInstance()
    }

    fun getDistrict(name: String) {
        apiRepository.getDistrict(name)
    }

    fun getDistrictLD(): LiveData<List<String>> {
        return apiRepository.getDistrictLD()
    }

    private val _formSenderValidation: MutableLiveData<List<FormFillType>> by lazy {
        MutableLiveData<List<FormFillType>>()
    }
    val formSenderValidation: LiveData<List<FormFillType>>
        get() = _formSenderValidation

    private val _formReceiverValidation: MutableLiveData<List<FormFillType>> by lazy {
        MutableLiveData<List<FormFillType>>()
    }
    val formReceiverValidation: LiveData<List<FormFillType>>
        get() = _formReceiverValidation

    var receiverName: String? = null
    var senderName: String? = null
    var senderPhone: String? = null
    var receiverPhone: String? = null
    var receiverDistrict: String? = null
    var senderDistrict: String? = null

    init {
        val listFormInitial: List<FormFillType> = listOf(
            FormFillType.BLANK,
            FormFillType.BLANK,
            FormFillType.BLANK,
            FormFillType.BLANK
        )
        _formSenderValidation.value = listFormInitial
        _formReceiverValidation.value = listFormInitial
    }

    fun setSenderValidation(formFillType: FormFillType, index: Int) {
        val list = _formSenderValidation.value!!
        val mutableList = list.toMutableList()
        mutableList[index] = formFillType
        _formSenderValidation.value = mutableList
    }

    fun setReceiverValidation(formFillType: FormFillType, index: Int) {
        val list = _formReceiverValidation.value!!
        val mutableList = list.toMutableList()
        mutableList[index] = formFillType
        _formReceiverValidation.value = mutableList
    }

    fun saveReceiverData(): DestinationModel {
        Log.d(TAG, "$receiverName - $receiverDistrict - $receiverPhone")
        return DestinationModel(receiverName, receiverPhone, receiverDistrict)
    }

    fun saveSenderData(): OriginModel {
        Log.d(TAG, "$senderName - $senderDistrict - $senderPhone")
        return OriginModel(senderName, senderPhone, senderDistrict)
    }
}