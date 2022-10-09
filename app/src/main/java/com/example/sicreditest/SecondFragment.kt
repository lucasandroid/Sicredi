package com.example.sicreditest

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sicreditest.databinding.FragmentSecondBinding
import com.example.sicreditest.feature.eventList.model.EventDetailState
import com.example.sicreditest.feature.eventList.model.SicrediEvent
import com.example.sicreditest.feature.eventList.viewmodel.EventDetailViewmodel
import com.example.sicreditest.feature.eventList.viewmodel.EventListViewModel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val viewModel: EventDetailViewmodel by viewModels {EventDetailViewmodel.Factory}

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
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

    private fun setComponents(eventData: SicrediEvent) {
        getImageEvent(eventData.imageUrl)
        binding.eventTitleDetail.text = eventData.title
        binding.eventDescriptionDetail.text = eventData.description
    }

    private fun getImageEvent(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.mipmap.sicredi)
            .into(binding.imageEventDetail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}