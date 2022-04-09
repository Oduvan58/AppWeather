package by.geekbrains.appweather.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import by.geekbrains.appweather.R

class AlertDialogFragment : DialogFragment() {
    companion object {
        const val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireActivity())
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setTitle(getString(R.string.title_alert_dailog_fragment))
            .setMessage(getString(R.string.message_alert_dailog_fragment))
            .setPositiveButton(getString(R.string.ttext_button_alert_dailog_fragment)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()
}