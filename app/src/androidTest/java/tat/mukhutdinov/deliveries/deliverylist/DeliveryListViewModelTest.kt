package tat.mukhutdinov.deliveries.deliverylist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.delivery.ui.boundary.DeliveryDomain
import tat.mukhutdinov.deliveries.deliverylist.ui.DeliveryListViewModel
import tat.mukhutdinov.deliveries.deliverylist.ui.DeliveryListViewModelDirections
import tat.mukhutdinov.deliveries.infrastructure.model.DataState
import tat.mukhutdinov.deliveries.infrastructure.model.Listing
import tat.mukhutdinov.deliveries.util.RecyclerViewItemCountAssertion
import tat.mukhutdinov.deliveries.util.asPagedList
import tat.mukhutdinov.deliveries.util.delivery1
import tat.mukhutdinov.deliveries.util.delivery2
import tat.mukhutdinov.deliveries.util.delivery3

@RunWith(AndroidJUnit4::class)
class DeliveryListViewModelTest : KoinTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    lateinit var scenario: FragmentScenario<DeliveryListViewModel>

    private val deliveryDomainMock = mockk<DeliveryDomain>()

    private val listingMock = mockk<Listing<Delivery>>()

    private val deliveries = listOf(delivery1, delivery2, delivery3)

    @Before
    fun setup() {
        every {
            deliveryDomainMock.getDeliveries()
        } returns listingMock

        loadKoinModules(module)
    }

    @Test
    fun deliveriesAreShown_onSuccessfulLoad() {
        every {
            listingMock.pagedList
        } returns deliveries.asPagedList()

        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)

        onView(withId(R.id.deliveries)).check(RecyclerViewItemCountAssertion(deliveries.size))

        deliveries.forEach {
            onView(withText(it.description)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            onView(withText(it.address)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    @Test
    fun progressBarIsShown_onDataLoadingState() {
        every {
            listingMock.dataState
        } returns MutableLiveData(DataState.Loading)

        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)

        onView(withId(R.id.loading)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun errorMessageIsShown_onDataErrorState() {
        val errorMessage = "Failed to load data"

        every {
            listingMock.dataState
        } returns MutableLiveData(DataState.Error(errorMessage))

        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)

        onView(withText(errorMessage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withText(R.string.retry)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun retryIsCalled_onRetryClick() {
        val errorMessage = "Failed to load data"

        every {
            listingMock.dataState
        } returns MutableLiveData(DataState.Error(errorMessage))

        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)

        onView(withText(R.string.retry)).perform(click())

        verify { listingMock.retry() }
    }

    @Test
    fun refreshIsCalled_onTopToBottomSwipe() {
        every {
            listingMock.pagedList
        } returns deliveries.asPagedList()

        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)

        onView(withId(R.id.deliveries)).perform(swipeDown())

        verify { listingMock.refresh() }
    }

    @Test
    fun navigationIsPerformed_onDeliveryClick() {
        every {
            listingMock.pagedList
        } returns deliveries.asPagedList()


        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)

        val navControllerMock = mockk<NavController>()
        scenario.onFragment {
            // runs on main thread
            Navigation.setViewNavController(it.requireView(), navControllerMock)
        }

        onView(withText(deliveries[0].description)).perform(click())

        verify { navControllerMock.navigate(DeliveryListViewModelDirections.toDetails(deliveries[0]), any<FragmentNavigator.Extras>()) }
    }

    private val module = module(override = true) {
        factory { (_: CoroutineScope) ->
            deliveryDomainMock
        }
    }
}