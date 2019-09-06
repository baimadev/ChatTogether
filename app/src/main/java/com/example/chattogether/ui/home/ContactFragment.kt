package com.example.chattogether.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.chattogether.R
import com.example.chattogether.databinding.FragmentContactBinding

class ContactFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentContactBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        return binding.root
    }
}