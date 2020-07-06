package com.ijniclohot.logkarmobilechallenge.feature.formulir

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ijniclohot.logkarmobilechallenge.R
import com.ijniclohot.logkarmobilechallenge.adapters.ReferenceListAdapter
import com.ijniclohot.logkarmobilechallenge.utils.FormFillType
import com.ijniclohot.logkarmobilechallenge.utils.OnRemoveReference
import com.ijniclohot.logkarmobilechallenge.utils.validateJlhPesanan
import com.ijniclohot.logkarmobilechallenge.utils.validateText
import com.ijniclohot.logkarmobilechallenge.viewmodel.FormulirViewModel
import com.ijniclohot.logkarmobilechallenge.viewmodel.factory.FormulirVMFactory
import kotlinx.android.synthetic.main.date_time_picker_dialog.view.*
import kotlinx.android.synthetic.main.fragment_formulir.view.*
import kotlinx.android.synthetic.main.input_reference_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FormulirFragment : Fragment(), OnRemoveReference {
    private lateinit var formulirViewModel: FormulirViewModel
    private lateinit var truckTypeAdapter: ArrayAdapter<String>
    private lateinit var capacityAdapter: ArrayAdapter<Int>
    private lateinit var commodityAdapter: ArrayAdapter<String>
    private lateinit var unitAdapter: ArrayAdapter<String>
    private val listTruckId: ArrayList<Int> = ArrayList()
    private lateinit var dateTimeDialogView: View
    private lateinit var dateTimePickerDialog: AlertDialog
    private lateinit var referenceDialogView: View
    private lateinit var referenceListAdapter: ReferenceListAdapter
    private lateinit var referenceDialog: AlertDialog
    private lateinit var formulirVMFactory: FormulirVMFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_formulir, container, false)

        //init adapter
        initAdapter()

        initView(view)

        //onTruckClick
        setTruckListOnClick(view)

        //Init Observer
        setTruckObserver(view)
        setCapacityObserver(view)
        setCommodityObserver(view)
        setReferenceObserver(view)
        setReferenceListObserver(view)

        return view
    }

    private fun initAdapter() {
        truckTypeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ArrayList())
        truckTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        capacityAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ArrayList())
        capacityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        commodityAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ArrayList())
        commodityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        unitAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ArrayList())
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun initView(view: View) {
        //INITIALIZE VIEW MODEL
        formulirVMFactory = FormulirVMFactory(requireContext())
        formulirViewModel =
            ViewModelProvider(requireActivity(), formulirVMFactory)[FormulirViewModel::class.java]

        //RETRIEVE INITIAL DATA
        formulirViewModel.getTruckList()
        formulirViewModel.getReferenceString()
        formulirViewModel.getReferenceSetString()

        //SET UI
        view.truck_type_spinner.adapter = truckTypeAdapter
        view.capacity_spinner.adapter = capacityAdapter
        view.capacity_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                formulirViewModel.capacity = capacityAdapter.getItem(position)
            }

        }
        view.unit_category_spinner.adapter = commodityAdapter
        view.unit_category_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    formulirViewModel.commodityName = commodityAdapter.getItem(p2).toString()
                }

            }
        view.unit_spinner.adapter = unitAdapter
        view.edt_unit.isEnabled = false
        view.unit_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                view.edt_unit.setText(unitAdapter.getItem(p2))
                formulirViewModel.unit = unitAdapter.getItem(p2).toString()
            }
        }

        view.edt_jlh_pesanan.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (validateJlhPesanan(p0.toString()).isNotEmpty()) {
                    formulirViewModel.setValidation(FormFillType.INVALID, 4)
                    view.edt_jlh_pesanan_layout.error = validateJlhPesanan(p0.toString())
                } else {
                    view.edt_jlh_pesanan_layout.error = null
                    formulirViewModel.setValidation(FormFillType.VALID, 4)
                    formulirViewModel.quantityOrder = p0?.toString()?.toInt()
                }
            }
        })

        view.edt_notes.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (validateText(p0.toString()).isNotEmpty()) {
                    formulirViewModel.setValidation(FormFillType.INVALID, 7)
                    view.edt_notes_layout.error = validateText(p0.toString())
                } else {
                    view.edt_notes_layout.error = null
                    formulirViewModel.setValidation(FormFillType.VALID, 7)
                }
                formulirViewModel.note = p0.toString()
            }
        })


        //DATE TIME PICKER
        dateTimeDialogView = View.inflate(requireContext(), R.layout.date_time_picker_dialog, null)
        dateTimePickerDialog = AlertDialog.Builder(requireContext()).create()

        //REFERENCE DIALOG
        referenceDialogView = View.inflate(requireContext(), R.layout.input_reference_layout, null)
        referenceDialog = AlertDialog.Builder(requireContext()).create()
        referenceDialogView.btn_add_reference.setOnClickListener {
            if (referenceDialogView.edt_input_reference.text.toString().isNotEmpty()) {
                formulirViewModel.saveReference(referenceDialogView.edt_input_reference.text.toString())
                referenceDialogView.edt_input_reference.text!!.clear()
                formulirViewModel.getReferenceString()
                formulirViewModel.getReferenceSetString()
                referenceDialog.dismiss()
            }
        }

        referenceListAdapter =
            ReferenceListAdapter(requireContext(), R.layout.reference_item, ArrayList(), this)
        referenceDialogView.list_view_reference.adapter = referenceListAdapter

        view.edt_calendar.setOnClickListener {
            showCalendar(view)
        }

        view.edt_reference_number.setOnClickListener {
            showReferenceDialog()
        }
    }

    private fun showReferenceDialog() {
        referenceDialog.setView(referenceDialogView)
        referenceDialog.show()
    }

    private fun showCalendar(view: View) {
        val calendar = Calendar.getInstance()
        val nextDay = calendar.clone() as Calendar
        nextDay.add(Calendar.DATE, 1)

        dateTimeDialogView.date_picker.minDate = nextDay.timeInMillis
        dateTimeDialogView.date_picker.maxDate = nextDay.timeInMillis

        dateTimePickerDialog.setView(dateTimeDialogView)
        dateTimePickerDialog.show()

        dateTimeDialogView.date_time_set.setOnClickListener {
            val dateTimeToShow: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                "${SimpleDateFormat(
                    "dd MMMM yyyy",
                    Locale.US
                ).format(nextDay.time)} ${dateTimeDialogView.time_picker.hour}:${dateTimeDialogView.time_picker.minute}"
            } else {
                SimpleDateFormat("dd MMMM yyyy", Locale.US).format(nextDay.time)
            }

            view.edt_calendar.setText(dateTimeToShow)
            formulirViewModel.setValidation(FormFillType.VALID, 6)
            formulirViewModel.startDeliveryDate = dateTimeToShow
            dateTimePickerDialog.dismiss()
        }

    }

    private fun setTruckObserver(view: View) {
        formulirViewModel.trucks.observe(requireActivity(), Observer {
            val list = ArrayList<String>()
            listTruckId.clear()
            val listUnit = ArrayList<String>()

            it.forEach { truckType ->
                list.add(truckType.truckType)
                listTruckId.add(truckType.truckTypeId)
                listUnit.addAll(truckType.unitList)
            }

            formulirViewModel.setValidation(FormFillType.VALID, 0)
            formulirViewModel.setValidation(FormFillType.VALID, 2)

            truckTypeAdapter.clear()
            truckTypeAdapter.addAll(list)
            truckTypeAdapter.notifyDataSetChanged()

            formulirViewModel.truckType = it[0].truckType

            unitAdapter.clear()
            unitAdapter.addAll(listUnit)
            unitAdapter.notifyDataSetChanged()

            formulirViewModel.unit = it[0].unitList[0]
        })
    }

    private fun setCapacityObserver(view: View) {
        formulirViewModel.capacities.observe(requireActivity(), Observer {
            capacityAdapter.clear()
            capacityAdapter.addAll(it)
            capacityAdapter.notifyDataSetChanged()
            formulirViewModel.setValidation(FormFillType.VALID, 1)
            formulirViewModel.capacity = it[0]
        })
    }

    private fun setCommodityObserver(view: View) {
        formulirViewModel.commodities.observe(requireActivity(), Observer {
            val list = ArrayList<String>()
            it.forEach { commodity ->
                list.add(commodity.commodityCategory)
            }
            commodityAdapter.clear()
            commodityAdapter.addAll(list)
            commodityAdapter.notifyDataSetChanged()
            formulirViewModel.setValidation(FormFillType.VALID, 3)
            formulirViewModel.commodityName = it[0].commodityCategory
        })
    }

    private fun setReferenceObserver(view: View) {
        formulirViewModel.numOfReference.observe(requireActivity(), Observer {
            val string = "$it Nomor Referensi"
            view.edt_reference_number.setText(string)
            if (it == 10) {
                referenceDialogView.btn_add_reference.isEnabled = false
            }
        })
    }

    private fun setReferenceListObserver(view: View) {
        formulirViewModel.reference.observe(requireActivity(), Observer {
            referenceListAdapter.clear()
            referenceListAdapter.addAll(it)
            referenceListAdapter.notifyDataSetChanged()
            formulirViewModel.refNo = it
        });
    }

    private fun setTruckListOnClick(views: View) {
        views.truck_type_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    CoroutineScope(IO).launch {
                        launch {
                            formulirViewModel.getTruckCapacity(listTruckId[position])
                        }
                        launch {
                            formulirViewModel.getTruckCommodities(listTruckId[position])
                        }
                    }
                    formulirViewModel.truckType = truckTypeAdapter.getItem(position)
                }
            }
    }

    override fun removeReference(reference: String) {
        formulirViewModel.removeReference(reference)
        formulirViewModel.getReferenceString()
        formulirViewModel.getReferenceSetString()
    }


}