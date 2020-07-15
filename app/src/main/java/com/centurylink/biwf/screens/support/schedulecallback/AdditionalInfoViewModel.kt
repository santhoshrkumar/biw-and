package com.centurylink.biwf.screens.support.schedulecallback

import com.centurylink.biwf.base.BaseViewModel
import com.centurylink.biwf.service.impl.workmanager.ModemRebootMonitorService
import javax.inject.Inject

class AdditionalInfoViewModel @Inject constructor(
    modemRebootMonitorService: ModemRebootMonitorService
) : BaseViewModel(modemRebootMonitorService)