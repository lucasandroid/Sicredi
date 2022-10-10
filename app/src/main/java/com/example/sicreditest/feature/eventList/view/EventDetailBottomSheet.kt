package com.example.sicreditest.feature.eventList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sicreditest.databinding.BottomsheetEventDetailBinding
import com.example.sicreditest.feature.eventList.model.CheckinData
import com.example.sicreditest.feature.eventList.viewmodel.CheckinViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EventDetailBottomSheet(private val eventId: Int): BottomSheetDialogFragment() {

    private val viewmodel: CheckinViewModel by viewModels {
        CheckinViewModel.Factory
    }

    private var _binding: BottomsheetEventDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetEventDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkinConfirmar.setOnClickListener {
            val name = binding.nameCheckin.text.toString()
            val email = binding.emailCheckin.text.toString()
            val checkinData = CheckinData(eventId, name, email)
            viewmodel.sendCheckin(checkinData)
        }
    }

    companion object {
        const val TAG = "EventDetailBottomSheet"
    }
}