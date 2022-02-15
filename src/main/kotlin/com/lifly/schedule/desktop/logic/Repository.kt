package com.lifly.schedule.desktop.logic

import com.lifly.schedule.desktop.logic.network.NormalScheduleNetwork
import kotlinx.coroutines.flow.flow

object Repository {
    fun getId() = flow {
        emit(NormalScheduleNetwork.getId())
    }
    suspend fun getId2() = NormalScheduleNetwork.getId()
}