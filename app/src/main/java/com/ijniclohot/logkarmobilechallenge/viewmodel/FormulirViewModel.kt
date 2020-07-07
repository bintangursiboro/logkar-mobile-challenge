package com.ijniclohot.logkarmobilechallenge.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijniclohot.logkarmobilechallenge.models.CommodityModel
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import com.ijniclohot.logkarmobilechallenge.models.TransporterModel
import com.ijniclohot.logkarmobilechallenge.models.TruckModel
import com.ijniclohot.logkarmobilechallenge.repository.ApiRepository
import com.ijniclohot.logkarmobilechallenge.repository.LocalRepository
import com.ijniclohot.logkarmobilechallenge.utils.FormFillType
import com.ijniclohot.logkarmobilechallenge.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormulirViewModel(private val context: Context) : ViewModel() {

    companion object {
        const val TAG = "FormulirViewModel"
    }

    private val repository: ApiRepository by lazy {
        ApiRepository()
    }

    private val sharedPrefUtils by lazy {
        SharedPrefUtils(context)
    }

    private val _numOfReference by lazy {
        MutableLiveData<Int>()
    }

    val numOfReference: LiveData<Int>
        get() = _numOfReference

    private val _references by lazy {
        MutableLiveData<List<String>>()
    }

    val reference: LiveData<List<String>>
        get() = _references

    val trucks: LiveData<List<TruckModel>> by lazy {
        repository.trucks
    }

    val commodities: LiveData<List<CommodityModel>> by lazy {
        repository.commodities
    }

    val capacities: LiveData<List<Int>> by lazy {
        repository.capacities
    }

    private val _formulirValidation: MutableLiveData<List<FormFillType>> by lazy {
        MutableLiveData<List<FormFillType>>()
    }
    val formulirValidation: LiveData<List<FormFillType>>
        get() = _formulirValidation

    var truckType: String? = null
    var capacity: Int? = null
    var unit: String? = null
    var refNo: List<String>? = null
    var startDeliveryDate: String? = null
    var note: String? = null
    var commodityName: String? = null
    var quantityOrder: Int? = null

    private val initialFormList: List<FormFillType> = listOf(
        FormFillType.BLANK,
        FormFillType.BLANK,
        FormFillType.BLANK,
        FormFillType.BLANK,
        FormFillType.BLANK,
        FormFillType.VALID,
        FormFillType.BLANK,
        FormFillType.BLANK
    )

    init {
        _formulirValidation.value = initialFormList
        refNo = _references.value
    }


    fun setValidation(fillType: FormFillType, index: Int) {
        (initialFormList as MutableList)[index] = fillType
        _formulirValidation.value = initialFormList
    }

    fun getTruckList() {
        repository.getTruckList()
    }

    fun getTruckCommodities(id: Int) {
        repository.getTruckCommodities(id)
    }

    fun getTruckCapacity(id: Int) {
        repository.getTruckCapacities(id)
    }

    fun getReferenceString() {
        CoroutineScope(IO).launch {
            if (sharedPrefUtils.getReference() == null) {
                _numOfReference.postValue(0)
            } else {
                _numOfReference.postValue(sharedPrefUtils.getReference()!!.size)
            }
        }
    }

    fun getReferenceSetString() {
        CoroutineScope(IO).launch {
            val mutableReference = sharedPrefUtils.getReference()
            if (mutableReference != null) {
                val newList = mutableReference.toMutableList()
                _references.postValue(newList)
            } else {
                _references.postValue(ArrayList())
            }
        }
    }

    fun removeReference(reference: String) {
        CoroutineScope(IO).launch {
            sharedPrefUtils.removeReference(reference)
        }
    }

    fun saveReference(reference: String) {
        CoroutineScope(IO).launch {
            sharedPrefUtils.saveReference(reference)
        }
    }

    fun saveFormulir(): TransporterModel {
        Log.d(
            TAG,
            "$truckType - $capacity - $quantityOrder - $unit - $refNo reference - on $startDeliveryDate - $note - $commodityName"

        )
        return TransporterModel(
            truckType,
            capacity,
            unit,
//            refNo,
            startDeliveryDate,
            note,
            commodityName,
            quantityOrder
        )
    }

}