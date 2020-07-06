package com.ijniclohot.logkarmobilechallenge.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijniclohot.logkarmobilechallenge.viewmodel.FormulirViewModel

class FormulirVMFactory(private val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormulirViewModel::class.java)) {
            return FormulirViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}