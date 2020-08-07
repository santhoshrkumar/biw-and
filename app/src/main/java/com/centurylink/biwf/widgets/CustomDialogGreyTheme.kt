package com.centurylink.biwf.widgets

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.centurylink.biwf.R
import kotlinx.android.synthetic.main.widget_dialog_default.view.dialog_message
import kotlinx.android.synthetic.main.widget_dialog_default.view.dialog_title
import kotlinx.android.synthetic.main.widget_dialog_default.view.negative_cta
import kotlinx.android.synthetic.main.widget_dialog_default.view.positive_cta

open class CustomDialogGreyTheme(
    private val callback: (buttonType: Int) -> Unit
) : DialogFragment() {

    lateinit var title: String
    lateinit var message: String
    lateinit var positiveText: String
    lateinit var negativeText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = arguments!!.getString(KEY_TITLE, "")
            message = arguments!!.getString(KEY_MESSAGE, "")
            positiveText = arguments!!.getString(KEY_POSITIVE, "")
            negativeText = arguments!!.getString(KEY_NEGATIVE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.widget_dialog_default, container, false)
        rootView.dialog_title.text = title
        if (message.isNotEmpty()) {
            rootView.dialog_message.visibility = View.VISIBLE
            rootView.dialog_message.text = message
        }
        rootView.positive_cta.text = positiveText
        rootView.negative_cta.text = negativeText
        rootView.positive_cta.setOnClickListener {
            dismiss()
            callback(AlertDialog.BUTTON_POSITIVE)
        }
        rootView.negative_cta.setOnClickListener {
            dismiss()
            callback(AlertDialog.BUTTON_NEGATIVE)
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return rootView
    }

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"
        private const val KEY_POSITIVE = "positive"
        private const val KEY_NEGATIVE = "negative"

        operator fun invoke(
            title: String,
            message: String,
            positiveText: String,
            negativeText: String,
            callback: (buttonType: Int) -> Unit
        ): CustomDialogGreyTheme {
            return CustomDialogGreyTheme(callback).apply {
                arguments = Bundle().apply {
                    putString(KEY_TITLE, title)
                    putString(KEY_MESSAGE, message)
                    putString(KEY_POSITIVE, positiveText)
                    putString(KEY_NEGATIVE, negativeText)
                }
            }
        }
    }
}
