package com.ijniclohot.logkarmobilechallenge

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ijniclohot.logkarmobilechallenge.adapters.MainPagerAdapter
import com.ijniclohot.logkarmobilechallenge.utils.FormFillType
import com.ijniclohot.logkarmobilechallenge.viewmodel.FormViewModel
import com.ijniclohot.logkarmobilechallenge.viewmodel.FormulirViewModel
import com.ijniclohot.logkarmobilechallenge.viewmodel.MainViewModel
import com.ijniclohot.logkarmobilechallenge.viewmodel.factory.FormulirVMFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_formulir.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var fragmentManager: FragmentManager

    private lateinit var viewPager: ViewPager

    private lateinit var tabLayout: TabLayout

    private lateinit var formViewModel: FormViewModel

    private lateinit var formulirViewModel: FormulirViewModel

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        formViewModel = ViewModelProvider(this)[FormViewModel::class.java]

        formulirViewModel =
            ViewModelProvider(this, FormulirVMFactory(this))[FormulirViewModel::class.java]

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setObserver()

        initView()
    }

    private fun setObserver() {
        formViewModel.formSenderValidation.observe(this, Observer {

            Log.d(TAG, it.toString())

            if (it.contains(FormFillType.INVALID)) {
                tabLayout.getTabAt(0)?.setIcon(R.mipmap.ic_incomplete_round)
                mainViewModel.setValidation(FormFillType.INVALID, 0)
            }

            if (!it.contains(FormFillType.INVALID) && !it.contains(FormFillType.BLANK)) {
                tabLayout.getTabAt(0)?.setIcon(R.mipmap.ic_complete_round)
                mainViewModel.setValidation(FormFillType.VALID, 0)
            }
        })

        formViewModel.formReceiverValidation.observe(this, Observer {
            Log.d(TAG, it.toString())

            if (it.contains(FormFillType.INVALID)) {
                tabLayout.getTabAt(1)?.setIcon(R.mipmap.ic_incomplete_round)
                mainViewModel.setValidation(FormFillType.INVALID, 1)
            }

            if (!it.contains(FormFillType.INVALID) && !it.contains(FormFillType.BLANK)) {
                tabLayout.getTabAt(1)?.setIcon(R.mipmap.ic_complete_round)
                mainViewModel.setValidation(FormFillType.VALID, 1)
            }
        })

        formulirViewModel.formulirValidation.observe(this, Observer {
            Log.d(TAG, it.toString())
            if (it.contains(FormFillType.INVALID)) {
                tabLayout.getTabAt(2)?.setIcon(R.mipmap.ic_incomplete_round)
                mainViewModel.setValidation(FormFillType.INVALID, 2)
            }

            if (!it.contains(FormFillType.INVALID) && !it.contains(FormFillType.BLANK)) {
                tabLayout.getTabAt(2)?.setIcon(R.mipmap.ic_complete_round)
                mainViewModel.setValidation(FormFillType.VALID, 2)
            }
        })

        mainViewModel.tabValidation.observe(this, Observer {
            Log.d(TAG + "TAB", it.toString())
            if (it.contains(FormFillType.INVALID)) {
                btn_selesai.isClickable = false
                btn_selesai.isEnabled = false
                btn_selesai.setBackgroundColor(ContextCompat.getColor(this, R.color.disabledGrey))
            } else if (!it.contains(FormFillType.INVALID) && !it.contains(FormFillType.BLANK)) {
                btn_selesai.isClickable = true
                btn_selesai.isEnabled = true
                btn_selesai.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreenBtn))
            }
        })
    }

    private fun initView() {
        //ViewPage
        viewPager = findViewById(R.id.main_view_pager)
        val mainViewPagerAdapter = MainPagerAdapter(supportFragmentManager)
        viewPager.adapter = mainViewPagerAdapter

        btn_selesai.isClickable = false
        btn_selesai.isEnabled = false
        btn_selesai.setBackgroundColor(ContextCompat.getColor(this, R.color.disabledGrey))

        btn_selesai.setOnClickListener {
            formViewModel.saveReceiverData()
            formViewModel.saveSenderData()
            formulirViewModel.saveFormulir()
        }

        //Table Layout
        tabLayout = findViewById(R.id.tab_main)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.setIcon(R.mipmap.ic_no_form_round)
        tabLayout.getTabAt(1)?.setIcon(R.mipmap.ic_no_form_round)
        tabLayout.getTabAt(2)?.setIcon(R.mipmap.ic_no_form_round)
    }
}