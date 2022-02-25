package com.ozcoin.cookiepang.ui.editprofile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentEditProfileBinding
import com.ozcoin.cookiepang.ui.MainActivityViewModel
import com.ozcoin.cookiepang.utils.BitmapUtil
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val editProfileFragmentViewModel by viewModels<EditProfileFragmentViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_profile
    }

    override fun initView() {
        with(binding) {
            pageName = "프로필 변경"
            viewModel = editProfileFragmentViewModel
        }
    }

    override fun initListener() {
        setupMaxLengthCaptionListener()
    }

    private fun setupMaxLengthCaptionListener() {
        binding.etUserIntroduce.addTextChangedListener {
            editProfileFragmentViewModel.emitIntroduceLength(it?.length ?: 0)
        }
    }

    override fun initObserve() {
        observeEvent(editProfileFragmentViewModel)
        observeEditProfileEvent()
        editProfileFragmentViewModel.uiStateObserver = UiStateObserver(
            mainActivityViewModel::updateUiState
        )
        editProfileFragmentViewModel.eventObserver = EventObserver(
            mainActivityViewModel::updateEvent
        )
    }

    private fun observeEditProfileEvent() {
        viewLifecycleScope.launch {
            editProfileFragmentViewModel.editProfileEventFlow.collect {
                when (it) {
                    is EditProfileEvent.OpenCamera -> {
                        isThumbnailEdit = it.isThumbnail
                        openCamera()
                    }
                    is EditProfileEvent.OpenGallery -> {
                        isThumbnailEdit = it.isThumbnail
                        openGallery()
                    }
                }
            }
        }
    }

    private var isThumbnailEdit = false

    private fun openCamera() {
        startCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    private val startCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Timber.d("camera result ok")
                it.data?.extras?.get("data")?.let { result ->
                    if (result is Bitmap)
                        updateImg(result)
                }
            } else {
                Timber.d("camera result not ok")
            }
        }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        startGallery.launch(intent)
    }

    private val startGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Timber.d("gallery result ok")
                it.data?.data?.let { uri ->
                    updateImg(BitmapUtil.decodeBitmap(requireContext(), uri))
                }
            } else {
                Timber.d("gallery result not ok")
            }
        }

    private fun updateImg(bitmap: Bitmap?) {
        val imageView: ImageView
        val requestOptions: RequestOptions
        if (isThumbnailEdit) {
            imageView = binding.ivUserThumbnail
            requestOptions = RequestOptions().circleCrop().centerCrop()
            editProfileFragmentViewModel.updateUserThumbnail(bitmap)
        } else {
            imageView = binding.ivProfileBackgroundImg
            requestOptions = RequestOptions().centerCrop()
            editProfileFragmentViewModel.updateUserBackgroundImg(bitmap)
        }

        Glide.with(imageView.context)
            .load(bitmap)
            .apply(requestOptions)
            .into(imageView)
    }

    private fun updateImg(uri: Uri) {
        val imageView: ImageView
        val requestOptions: RequestOptions
        if (isThumbnailEdit) {
            imageView = binding.ivUserThumbnail
            requestOptions = RequestOptions().circleCrop().centerCrop()
        } else {
            imageView = binding.ivProfileBackgroundImg
            requestOptions = RequestOptions().centerCrop()
        }

        Glide.with(imageView.context)
            .load(uri)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun init() {
        editProfileFragmentViewModel.loadLoginUserInfo()
    }
}
