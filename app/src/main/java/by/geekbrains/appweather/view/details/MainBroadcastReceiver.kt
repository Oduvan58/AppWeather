package by.geekbrains.appweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import by.geekbrains.appweather.R

class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val isConnectivity =
            intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
        if (!isConnectivity) onConnectionFound(context) else onConnectionLost(context)
    }

    private fun onConnectionLost(context: Context) {
        Toast.makeText(context, context.getString(R.string.connection_lost), Toast.LENGTH_LONG)
            .show()
    }

    private fun onConnectionFound(context: Context) {
        Toast.makeText(context, context.getString(R.string.connection_found), Toast.LENGTH_LONG)
            .show()
    }
}