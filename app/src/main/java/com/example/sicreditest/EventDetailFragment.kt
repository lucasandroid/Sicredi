package com.example.sicreditest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sicreditest.databinding.FragmentEventDetailBinding
import com.example.sicreditest.feature.eventList.model.DetailEvent
import com.example.sicreditest.feature.eventList.model.EventDetailState
import com.example.sicreditest.feature.eventList.util.StringUtils
import com.example.sicreditest.feature.eventList.view.EventDetailBottomSheet
import com.example.sicreditest.feature.eventList.viewmodel.EventDetailViewmodel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val viewModel: EventDetailViewmodel by viewModels {EventDetailViewmodel.Factory}

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        arguments?.let {
            viewModel.init(it)
        }
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        viewModel.eventDetailLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is EventDetailState.EventDetailSuccess -> {
                    setComponents(event.details)
                }
            }
        }
    }

    private fun setComponents(eventData: DetailEvent) {
        eventData.imageUrl?.let { getImageEvent(it) }
        binding.eventTitleDetail.text = eventData.title
        binding.eventDescriptionDetail.text = eventData.description
        //binding.eventDateDetail.text = getString(R.string.event_date, eventData.date)
        eventData.price?.let {
            binding.eventPriceDetail.text = getString(
                R.string.event_price, StringUtils.convertToCurrency(it)
            )
        }
        binding.eventShareDetail.setOnClickListener { 
            shareEvent(eventData)
        }
        binding.eventCheckinDetail.setOnClickListener { 
            showCheckinEvent(eventData)
        }
    }

    private fun showCheckinEvent(eventData: DetailEvent) {
        eventData.id?.let {
            EventDetailBottomSheet(it).show(parentFragmentManager, EventDetailBottomSheet.TAG)
        }
    }

    private fun shareEvent(eventData: DetailEvent) {

    }

    private fun getImageEvent(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.mipmap.sicredi)
            .into(binding.imageEventDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}