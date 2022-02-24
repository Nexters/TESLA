package com.ozcoin.cookiepang.ui.dialog

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ozcoin.cookiepang.databinding.DialogTwoBtnBinding
import com.ozcoin.cookiepang.domain.dialog.DialogContents
import timber.log.Timber

class TwoBtnDialog(
    private val dialogContents: DialogContents,
    private val callback: (Boolean) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogTwoBtnBinding
    private lateinit var size: Point

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTwoBtnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {
        binding.dialogContents = dialogContents
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getDisplaySize()
    }

    private fun getDisplaySize() {
        val windowManager =
            requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        size = Point()
        display.getSize(size)
    }

    private fun initListener() {
        binding.tvConfirmBtn.setOnClickListener {
            callback(true)
            dismiss()
        }
        binding.tvCancelBtn.setOnClickListener {
            callback(false)
            dismiss()
        }
        binding.tvContentsLink.setOnClickListener {
            if (dialogContents.dialogLink != null) {
                kotlin.runCatching {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(dialogContents.dialogLink.link)))
                }.onFailure {
                    Timber.e(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setWidth()
    }

    private fun setWidth() {
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}
