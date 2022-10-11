package com.example.sicreditest.feature.eventList.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sicreditest.R
import com.example.sicreditest.databinding.BottomsheetEventDetailBinding
import com.example.sicreditest.feature.eventList.model.CheckinData
import com.example.sicreditest.feature.eventList.viewmodel.state.EventCheckinState
import com.example.sicreditest.feature.eventList.viewmodel.CheckinViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EventDetailBottomSheet(private val eventId: Int): BottomSheetDialogFragment() {

    private val viewModel: CheckinViewModel by viewModels {
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
        setObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setcomponents()
    }

    private fun setcomponents() {
        binding.nameCheckin.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    viewModel.validateForm(s.toString(), binding.emailCheckin.text.toString())
                }
            }

        })
        binding.emailCheckin.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    viewModel.validateForm(binding.nameCheckin.text.toString(), s.toString())
                }
            }

        })
        binding.checkinConfirmar.setOnClickListener {
            sendCheckin()
        }
    }

    private fun setObservers() {
        viewModel.validForm.observe(viewLifecycleOwner) {
            binding.checkinConfirmar.isEnabled = it
        }

        viewModel.checkinResult.observe(viewLifecycleOwner) { state ->
            when(state) {
                is EventCheckinState.EventCheckinSuccess -> {
                    showSuccess()
                }
                is EventCheckinState.EventCheckinError -> {
                    showError()
                }
            }
            changeView()
        }

        viewModel.checkinLoading.observe(viewLifecycleOwner) { state ->
            when(state) {
                is EventCheckinState.CheckinShowLoading -> {
                    binding.checkinLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun changeView() {
        binding.checkinResult.visibility = View.VISIBLE
        binding.checkinFields.visibility = View.INVISIBLE
    }

    private fun showError() {
        binding.checkinImage.setImageResource(R.drawable.ic_error)
        binding.checkinMessage.text = getString(R.string.checkin_error)
    }

    private fun showSuccess() {
        binding.checkinImage.setImageResource(R.drawable.ic_success)
        binding.checkinMessage.text = getString(R.string.checkin_success)
    }

    private fun sendCheckin() {
        val name = binding.nameCheckin.text.toString()
        val email = binding.emailCheckin.text.toString()
        val checkinData = CheckinData(eventId, name, email)
        viewModel.sendCheckin(checkinData)
    }

    companion object {
        const val TAG = "EventDetailBottomSheet"
    }
}