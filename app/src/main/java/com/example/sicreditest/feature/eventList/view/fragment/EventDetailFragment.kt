package com.example.sicreditest.feature.eventList.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sicreditest.R
import com.example.sicreditest.databinding.FragmentEventDetailBinding
import com.example.sicreditest.feature.eventList.model.DetailEvent
import com.example.sicreditest.feature.eventList.viewmodel.state.EventDetailState
import com.example.sicreditest.feature.eventList.view.EventDetailBottomSheet
import com.example.sicreditest.feature.eventList.viewmodel.EventDetailViewmodel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val viewModel: EventDetailViewmodel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        initViewModel()
        initObservers()
        return binding.root
    }

    private fun initViewModel() {
        arguments?.let {
            viewModel.init(it)
        }
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
        eventData.date?.let {
            binding.eventDateDetail.text = getString(R.string.event_date, it)
        }

        binding.eventDescriptionDetail.text = eventData.description
        eventData.price?.let {
            binding.eventPriceDetail.text = getString(
                R.string.event_price, it
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
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, eventData.description)
            putExtra(Intent.EXTRA_TITLE, eventData.title)
            putExtra(Intent.EXTRA_SUBJECT, eventData.title)
            type = "text/plain"

        }, null)
        startActivity(share)
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