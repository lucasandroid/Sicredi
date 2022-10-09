package com.example.sicreditest

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sicreditest.databinding.FragmentFirstBinding
import com.example.sicreditest.feature.eventList.model.EventListState
import com.example.sicreditest.feature.eventList.model.SicrediEvent
import com.example.sicreditest.feature.eventList.view.ItemEventAdapter
import com.example.sicreditest.feature.eventList.viewmodel.EventListViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var eventList: RecyclerView
    private var listAdapter: ItemEventAdapter? = null
    private val binding get() = _binding!!
    private val viewModel : EventListViewModel by viewModels { EventListViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        setObservers()
        initComponents()
        viewModel.init()
        return binding.root
    }

    private fun initComponents() {
        val spanCount = getSpanSize()
        eventList = binding.eventList
        with(eventList){
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(context, spanCount)
            setHasFixedSize(true)
        }
    }

    private fun getSpanSize(): Int {
        return if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            3 else 2
    }

    private fun setObservers() {
        viewModel.eventListLiveData.observe(viewLifecycleOwner, Observer { event ->
            when(event) {
                is EventListState.EventListSuccess -> {
                    listAdapter = ItemEventAdapter(event.list){
                        setItemClickListener(it)
                    }
                    eventList.adapter = listAdapter
                }
                is EventListState.EventListError -> {
                    event.error
                }
            }
        })
    }

    private fun setItemClickListener(event: SicrediEvent) {
        val bundle = createBundle(event)
        findNavController().navigate(R.id.action_view_event_detail, bundle)
    }

    private fun createBundle(event: SicrediEvent): Bundle {

        val bundle = Bundle()
        with(bundle) {
            putSerializable("event", event)
        }
        return bundle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}