package com.ijniclohot.logkarmobilechallenge.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ijniclohot.logkarmobilechallenge.feature.form.FormFragment
import com.ijniclohot.logkarmobilechallenge.feature.formulir.FormulirFragment
import com.ijniclohot.logkarmobilechallenge.utils.FormFragmentType

class MainPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val fragment: Fragment?

        when (position) {
            0 -> {
                fragment =
                    FormFragment(
                        FormFragmentType.SENDER
                    )
                val bundle = Bundle()
                bundle.putString("message", "Fragment$position")
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                fragment =
                    FormFragment(
                        FormFragmentType.RECEIVER
                    )
                val bundle = Bundle()
                bundle.putString("message", "Fragment$position")
                fragment.arguments = bundle
                return fragment
            }
            else -> {
                fragment =
                    FormulirFragment()
                val bundle = Bundle()
                bundle.putString("message", "Fragment$position")
                fragment.arguments = bundle
                return fragment
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Asal"
            1 -> "Tujuan"
            else -> "Formulir"
        }
    }
}