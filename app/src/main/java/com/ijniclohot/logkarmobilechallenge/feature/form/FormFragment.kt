package com.ijniclohot.logkarmobilechallenge.feature.form

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ijniclohot.logkarmobilechallenge.R
import com.ijniclohot.logkarmobilechallenge.utils.FormFillType
import com.ijniclohot.logkarmobilechallenge.utils.FormFragmentType
import com.ijniclohot.logkarmobilechallenge.utils.validatePhoneNumber
import com.ijniclohot.logkarmobilechallenge.utils.validateSenderName
import com.ijniclohot.logkarmobilechallenge.viewmodel.FormViewModel
import kotlinx.android.synthetic.main.fragment_form.view.*
import java.util.*
import kotlin.collections.ArrayList

class FormFragment(private val formType: FormFragmentType) : Fragment() {
    private lateinit var phoneNumberEdtText: TextInputEditText
    private lateinit var phoneNumberLayout: TextInputLayout
    private lateinit var phoneNumberLabel: TextView

    private lateinit var nameEdtText: TextInputEditText
    private lateinit var nameLayout: TextInputLayout
    private lateinit var nameLabel: TextView

    private lateinit var districtEdtText: AutoCompleteTextView

    private lateinit var formViewModel: FormViewModel

    private lateinit var districtAdapter: ArrayAdapter<String>

    companion object {
        const val TAG = "Form Fragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form, container, false)

        initView(view)
        setObserver()

        return view
    }

    private fun initView(view: View) {

        //Textview initializtion
        nameLabel = view.findViewById(R.id.tvNama)
        phoneNumberLabel = view.findViewById(R.id.tvNomorHandphone)
        phoneNumberEdtText = view.findViewById(R.id.edtPhoneNumber)
        phoneNumberLayout = view.findViewById(R.id.edtLayoutPhoneNumber)
        nameEdtText = view.findViewById(R.id.edtSenderName)
        nameLayout = view.findViewById(R.id.edtSenderNameLayout)
        districtEdtText = view.findViewById(R.id.edtKecamatanatc)

        formViewModel = ViewModelProvider(requireActivity())[FormViewModel::class.java]

        districtAdapter =
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, ArrayList())
        districtAdapter.setNotifyOnChange(true)

        districtEdtText.setAdapter(districtAdapter)

        if (formType == FormFragmentType.RECEIVER) {
            nameLabel.text = getString(R.string.nama_receiver_label)
            phoneNumberLabel.text = getString(R.string.no_hp_receiver_label)
            phoneNumberEdtText.hint = getString(R.string.handphone_reveiver_hint)
            nameEdtText.hint = getString(R.string.nama_penerima_hint)
        }

        //Nomor handphone
        phoneNumberEdtText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (validatePhoneNumber(p0.toString()).isEmpty()) {
                    phoneNumberLayout.error = null
                    setValidation(FormFillType.VALID, 1)
                } else {
                    phoneNumberLayout.error = validatePhoneNumber(p0.toString())
                    setValidation(FormFillType.INVALID, 1)
                }

                if (formType == FormFragmentType.SENDER) formViewModel.senderPhone =
                    p0.toString() else formViewModel.receiverPhone = p0.toString()
            }

        })

        //Nama
        nameEdtText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (validateSenderName(p0.toString()).isEmpty()) {
                    nameLayout.error = null
                    setValidation(FormFillType.VALID, 0)
                } else {
                    nameLayout.error = validateSenderName(p0.toString())
                    setValidation(FormFillType.INVALID, 0)
                }
                if (formType == FormFragmentType.SENDER) formViewModel.senderName =
                    p0.toString() else formViewModel.receiverName = p0.toString()
            }
        })

        districtEdtText.addTextChangedListener(object : TextWatcher {
            private var timer: Timer? = null
            override fun afterTextChanged(p0: Editable?) {
                timer = Timer()
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        formViewModel.getDistrict(p0.toString())
                    }
                }, 2000)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer?.cancel()
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        setValidation(FormFillType.VALID, 2)
                    } else {
                        setValidation(FormFillType.INVALID, 2)
                    }
                }
                if (formType == FormFragmentType.SENDER) formViewModel.senderDistrict =
                    p0.toString() else formViewModel.receiverDistrict = p0.toString()
            }
        })

        //Alamat
        view.edtAlamat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        setValidation(FormFillType.VALID, 3)
                    } else {
                        setValidation(FormFillType.INVALID, 3)
                    }
                }

            }

        })


    }

    private fun setObserver() {
        if (formViewModel.getDistrictLD().hasObservers()) {
            formViewModel.getDistrictLD().removeObservers(this)
        }
        formViewModel.getDistrictLD().observe(requireActivity(), Observer {
            districtAdapter.clear()
            districtAdapter.addAll(it)
            districtAdapter.notifyDataSetChanged()
            districtEdtText.showDropDown()
        })
    }

    private fun setValidation(formFillType: FormFillType, index: Int) {
        if (formType == FormFragmentType.SENDER) {
            formViewModel.setSenderValidation(formFillType, index)
        } else {
            formViewModel.setReceiverValidation(formFillType, index)
        }
    }

}