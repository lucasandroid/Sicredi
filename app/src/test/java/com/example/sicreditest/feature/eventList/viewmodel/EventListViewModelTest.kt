package com.example.sicreditest.feature.eventList.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sicreditest.feature.eventList.datasource.EventSource
import com.example.sicreditest.feature.eventList.mapper.SicrediEventMapper
import com.example.sicreditest.feature.eventList.model.SicrediEvent
import com.example.sicreditest.feature.eventList.viewmodel.state.EventListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventListViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: EventSource

    @Mock
    private lateinit var mapper: SicrediEventMapper

    private lateinit var viewmodel: EventListViewModel

    @Mock
    private lateinit var observer: Observer<EventListState>

    @Mock
    private val event: SicrediEvent = mock(SicrediEvent::class.java)

    private lateinit var response: List<SicrediEvent>

    @Before
    fun setup(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockitoAnnotations.openMocks(this)
        response = listOf(event)
        viewmodel = EventListViewModel(repository, mapper)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `should load and hide progressbar`() = runBlocking {
        viewmodel.listLoading.observeForever(observer)
        val capture = ArgumentCaptor.forClass(EventListState::class.java)
        `when`(repository.getList()).thenReturn(response)
        viewmodel.init()

        verify(observer, times(2)).onChanged(capture.capture())
        Assert.assertTrue(capture.allValues.first() is (EventListState.ShowLoading))
        Assert.assertTrue(capture.allValues.last() is (EventListState.HideLoading))
    }

    @Test
    fun `should load list success`() = runBlocking {
        val captor = ArgumentCaptor.forClass(EventListState.EventListSuccess::class.java)
        val list = SicrediEventMapper().parseEventListResponseToDetailList(response)
        `when`(repository.getList()).thenReturn(response)
        `when`(mapper.parseEventListResponseToDetailList(response)).thenReturn(list)
        viewmodel.eventListLiveData.observeForever(observer)
        viewmodel.init()
        verify(observer).onChanged(captor.capture())
        Assert.assertEquals(list.size, captor.value.list.size)
    }

    @Test
    fun `should show error`() = runBlocking {
        val captor = ArgumentCaptor.forClass(EventListState.EventListError::class.java)
        `when`(repository.getList()).thenThrow(IllegalArgumentException())
        viewmodel.eventListLiveData.observeForever(observer)
        viewmodel.init()
        verify(observer).onChanged(captor.capture())
        Assert.assertTrue(captor.value is (EventListState.EventListError))
    }

    @Test
    fun `should convert response corretly`() {
        val list = SicrediEventMapper().parseEventListResponseToDetailList(response)
        Assert.assertEquals(1, list.size)
    }
}