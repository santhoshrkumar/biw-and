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
import kotlinx.android.synthetic.main.widget_popup.view.popup_cancel_btn
import kotlinx.android.synthetic.main.widget_popup.view.popup_message
import kotlinx.android.synthetic.main.widget_popup.view.popup_neutral_button
import kotlinx.android.synthetic.main.widget_popup.view.popup_positive_button
import kotlinx.android.synthetic.main.widget_popup.view.popup_title

open class CustomDialogBlueTheme(
    private val callback: (buttonType: Int) -> Unit
) : DialogFragment() {

    lateinit var title: String
    lateinit var message: String
    lateinit var buttonText: String
    var isErrorPopup: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = arguments!!.getString(KEY_TITLE, "")
            message = arguments!!.getString(KEY_MESSAGE, "")
            buttonText = arguments!!.getString(KEY_BUTTON_TEXT, "")
            isErrorPopup = arguments!!.getBoolean(KEY_IS_ERROR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.widget_popup, container, false)
        rootView.popup_title.text = title
        rootView.popup_message.text = message
        rootView.popup_cancel_btn.setOnClickListener {
            dismiss()
            callback(AlertDialog.BUTTON_NEGATIVE)
        }
        if (isErrorPopup) {
            rootView.popup_positive_button.text = buttonText
            rootView.popup_neutral_button.visibility = View.GONE
            rootView.popup_positive_button.setOnClickListener {
                dismiss()
                callback(AlertDialog.BUTTON_POSITIVE)
            }
        } else {
            rootView.popup_neutral_button.text = buttonText
            rootView.popup_positive_button.visibility = View.GONE
            rootView.popup_neutral_button.setOnClickListener {
                dismiss()
            }
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return rootView
    }

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"
        private const val KEY_BUTTON_TEXT = "button-text"
        private const val KEY_IS_ERROR = "is-error"

        operator fun invoke(
            title: String,
            message: String,
            buttonText: String,
            isErrorPopup: Boolean,
            callback: (buttonType: Int) -> Unit
        ): CustomDialogBlueTheme {
            return CustomDialogBlueTheme(callback).apply {
                arguments = Bundle().apply {
                    putString(KEY_TITLE, title)
                    putString(KEY_MESSAGE, message)
                    putString(KEY_BUTTON_TEXT, buttonText)
                    putBoolean(KEY_IS_ERROR, isErrorPopup)
                }
            }
        }
    }
}
