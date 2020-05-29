package com.centurylink.biwf.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.centurylink.biwf.base.BaseActivity
import com.centurylink.biwf.coordinators.HomeCoordinator
import com.centurylink.biwf.coordinators.Navigator
import com.centurylink.biwf.databinding.ActivityHomeBinding
import com.centurylink.biwf.screens.cancelsubscription.CancelSubscriptionDetailsActivity
import com.centurylink.biwf.screens.home.dashboard.DashboardFragment
import com.centurylink.biwf.utility.DaggerViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : BaseActivity(), DashboardFragment.GetStartedEventClickListener {

    @Inject
    lateinit var homeCoordinator: HomeCoordinator
    @Inject
    lateinit var factory: DaggerViewModelFactory
    @Inject
    lateinit var navigator: Navigator

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }
    private val adapter by lazy { TabsPagerRecyclerAdapter(this, this) }
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigator.observe(this)
        viewModel.myState.observeWith(homeCoordinator)

        initViews()
        initOnClicks()

        // Example: Listen to data emitted from Flow properties.
        // TODO Remove this example when we get some actual code here using this setup.
        viewModel.apply {
            testRestFlow.observe { Timber.d(it) }
            testRestErrorFlow.observe { Timber.e(it) }
        }
    }

    override fun onGetStartedClick(newUser: Boolean) {
        //her handle navigation functions and Tab visibility.
    }

    /**
     * Comparing the number of entries currently in the back stack to handle Back Press
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == CancelSubscriptionDetailsActivity.REQUEST_TO_ACCOUNT) {
            binding.vpDashboard.currentItem = 0
        }
    }

    private fun initViews() {
        viewModel.activeUserTabBarVisibility.observe {
            setupTabsViewPager(it)
        }
        viewModel.networkStatus.observe { binding.homeOnlineStatusBar.setOnlineStatus(it) }
    }

    fun launchSubscriptionActivity() {
        viewModel.onSubscriptionActivityClick()
    }

    private fun initOnClicks() {
        binding.homeOnlineStatusBar.setOnClickListener { viewModel.onOnlineToolbarClick() }
        binding.iBtnNotification.setOnClickListener { viewModel.onNotificationBellClicked() }
        binding.supportButton.setOnClickListener { viewModel.onSupportClicked() }
    }

    private fun setupTabsViewPager(it: Boolean) {
        binding.homeUpperTabs.visibility = if (it) View.VISIBLE else View.GONE
        binding.homeLowerTabs.visibility = if (it) View.GONE else View.VISIBLE
        binding.homeOnlineStatusBar.visibility = if (it) View.GONE else View.VISIBLE
        binding.vpDashboard.adapter = adapter
        if (it) {
            adapter.submitList(viewModel.upperTabHeaderList)
            TabLayoutMediator(binding.homeUpperTabs, binding.vpDashboard,
                TabLayoutMediator.OnConfigureTabCallback
                { tab, position -> tab.setText(viewModel.upperTabHeaderList[position].titleRes) }).attach()
        } else {
            adapter.submitList(viewModel.lowerTabHeaderList)
            TabLayoutMediator(binding.homeLowerTabs, binding.vpDashboard,
                TabLayoutMediator.OnConfigureTabCallback
                { tab, position -> tab.setText(viewModel.lowerTabHeaderList[position].titleRes) }).attach()
        }
    }

    companion object {
        fun newIntent(context: Context, boolean: Boolean): Intent {
            return Intent(context, HomeActivity::class.java)
                .putExtra("EXISTING_USER", boolean)
        }
    }
}