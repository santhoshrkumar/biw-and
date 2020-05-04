package com.centurylink.biwf.coordinators

import com.centurylink.biwf.utility.ObservableData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginCoordinator @Inject constructor(val navigator: Navigator) {

    fun observeThis(screenState: ObservableData<LoginCoordinatorDestinations>) {
        screenState.observable.subscribe {
            navigateTo(it)
        }
    }

    private fun navigateTo(destinations: LoginCoordinatorDestinations) {
        when (destinations) {
            LoginCoordinatorDestinations.LOGIN -> {
            }
            LoginCoordinatorDestinations.FORGOT_PASSWORD -> navigateToForgotPassword()
            LoginCoordinatorDestinations.LEARN_MORE -> navigateToLearnMore()
            LoginCoordinatorDestinations.HOME_NEW_USER -> navigateToHomeScreen(false)
            LoginCoordinatorDestinations.HOME_EXISTING_USER -> navigateToHomeScreen(true)
        }
    }

    private fun navigateToForgotPassword() {
        navigator.navigateToForgotPassword()
    }

    private fun navigateToLearnMore() {
        navigator.navigateToLearnMore()
    }

    private fun navigateToHomeScreen(existingUser: Boolean) {
        navigator.navigateToHomeScreen(existingUser)
    }
}

enum class LoginCoordinatorDestinations {
    FORGOT_PASSWORD, LEARN_MORE, HOME_NEW_USER, LOGIN, HOME_EXISTING_USER
}