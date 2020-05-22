package com.centurylink.biwf.screens.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.centurylink.biwf.R
import com.centurylink.biwf.base.BaseFragment
import com.centurylink.biwf.coordinators.DashboardCoordinator
import com.centurylink.biwf.databinding.FragmentDashboardBinding
import com.centurylink.biwf.model.notification.Notification
import com.centurylink.biwf.utility.DaggerViewModelFactory
import com.centurylink.biwf.utility.observe
import javax.inject.Inject

class DashboardFragment : BaseFragment() {

    override val lifecycleOwner: LifecycleOwner = this

    private var newUser: Boolean = false

    @Inject
    lateinit var dashboardCoordinator: DashboardCoordinator

    @Inject
    lateinit var factory: DaggerViewModelFactory
    private val dashboardViewModel by lazy {
        ViewModelProvider(this, factory).get(DashboardViewModel::class.java)
    }
    private var unreadNotificationList: MutableList<Notification> = mutableListOf()

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = false

        newUser = arguments!!.getBoolean(KEY_NEW_USER, newUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        /*Added dummy state variable to test layout for different scenarios */
        binding.states = newUser
        initOnClicks()
        getNotificationInformation()
        binding.executePendingBindings()
        dashboardViewModel.myState.observeWith(dashboardCoordinator)
        return binding.root
    }

    private fun getNotificationInformation() {
        dashboardViewModel.getNotificationDetails().observe(this) {
            when {
                it.status.isLoading() -> {
                }
                it.status.isSuccessful() -> {
                    dashboardViewModel.displaySortedNotifications(it.data!!.notificationlist)
                    displaySortedNotification()
                }
                it.status.isError() -> {
                }
            }
        }
    }

    private fun displaySortedNotification() {
        dashboardViewModel.getNotificationMutableLiveData().observe(viewLifecycleOwner, Observer {
            addNotificationStack(it)
        })
    }

    private fun addNotificationStack(notificationList: MutableList<Notification>) {
        unreadNotificationList = notificationList
        if (unreadNotificationList.isNotEmpty()) {
            when (unreadNotificationList.size) {
                1 -> {
                    binding.middleCard.visibility = View.GONE
                    binding.bottomCard.visibility = View.GONE
                }
                2 -> {
                    binding.bottomCard.visibility = View.GONE
                }
            }
            binding.notificationTitle.text = unreadNotificationList.get(0).name
            binding.notificationMsg.text = unreadNotificationList.get(0).description
        } else {
            binding.topCard.visibility = View.GONE
            binding.middleCard.visibility = View.GONE
            binding.bottomCard.visibility = View.GONE
        }
    }

    private fun initOnClicks() {
        binding.incStatus.appointmentChangeBtn.setOnClickListener { dashboardViewModel.getChangeAppointment() }
        binding.incWelcomeCard.welcomeCardCancelButton.setOnClickListener { hideWelcomeCard() }
        binding.notificationDismissButton.setOnClickListener {
            dashboardViewModel.markNotificationAsRead(unreadNotificationList.get(0))
            displaySortedNotification()
        }
        binding.topCard.setOnClickListener {
            dashboardViewModel.navigateToNotificationDetails(unreadNotificationList.get(0))
        }
    }

    private fun hideWelcomeCard() {
        binding.incWelcomeCard.root.visibility = View.GONE
    }

    companion object {
        const val KEY_UNREAD_HEADER: String = "UNREAD_HEADER"
        private const val KEY_NEW_USER = "NEW_USER"

        operator fun invoke(newUser: Boolean) = DashboardFragment().apply {
            arguments = Bundle().apply { putBoolean(KEY_NEW_USER, newUser) }
        }
    }
}