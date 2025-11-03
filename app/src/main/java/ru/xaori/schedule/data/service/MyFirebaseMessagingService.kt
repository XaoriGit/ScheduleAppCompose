package ru.xaori.schedule.data.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.xaori.schedule.domain.repository.SettingsRepository

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val settingsRepository: SettingsRepository by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")

        CoroutineScope(Dispatchers.IO).launch {
            settingsRepository.setFcmToken(token)
        }
    }
}
