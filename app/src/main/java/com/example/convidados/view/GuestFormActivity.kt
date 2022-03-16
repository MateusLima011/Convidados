package com.example.convidados.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.service.constants.GuestConstants
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private val mViewModel by lazy { ViewModelProvider(this)[GuestFormViewModel::class.java] }
    private lateinit var button: Button
    private lateinit var editName: EditText
    private lateinit var presence: RadioButton
    private lateinit var absent: RadioButton
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        setListeners()
        observe()
        loadData()

        absent = findViewById(R.id.radio_absent)
        presence = findViewById(R.id.radio_presence)
        editName = findViewById(R.id.edit_name)

    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save) {

            editName.text.toString()

            presence.isChecked

            mViewModel.save(mGuestId, editName.text.toString(), presence.isChecked)

        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        }
    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha!", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, {
            editName.setText(it.name)
            if (it.presence) {
                presence.isChecked = true
            } else {
                absent.isChecked = true
            }
        })
    }

    private fun setListeners() {
        button = findViewById(R.id.button_save)
        button.setOnClickListener(this)
    }
}