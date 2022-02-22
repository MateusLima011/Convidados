package com.example.convidados.viewmodel

import android.widget.EditText
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.service.model.GuestModel
import com.example.convidados.service.repository.GuestRepository

class GuestFormViewModel : ViewModel() {

    private var mGuestRepository: GuestRepository = GuestRepository()

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest

    fun save(name: EditText, presence: RadioButton) {
        val guest = GuestModel(name, presence)
        mGuestRepository.save(guest)
    }
}