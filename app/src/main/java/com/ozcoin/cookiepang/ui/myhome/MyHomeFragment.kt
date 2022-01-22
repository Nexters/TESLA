package com.ozcoin.cookiepang.ui.myhome

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.igalata.bubblepicker.BubblePickerListener
import com.igalata.bubblepicker.adapter.BubblePickerAdapter
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseFragment
import com.ozcoin.cookiepang.databinding.FragmentMyHomeBinding

class MyHomeFragment : BaseFragment<FragmentMyHomeBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        init()
    }


    private fun initView() {
//        setPicker()
    }

    private fun initListener() {
//        setPickerListener()
    }

    private fun init() {
        animSlideUpContents()
    }

//    private fun setPicker() {
//        val titles = resources.getStringArray(R.array.countries)
//        val colors = resources.obtainTypedArray(R.array.colors)
//        val images = resources.obtainTypedArray(R.array.images)
//
//        binding.pickerCookie.adapter = object : BubblePickerAdapter {
//
//            override val totalCount = titles.size
//
//            override fun getItem(position: Int): PickerItem {
//                return PickerItem().apply {
//                    title = titles[position]
//                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
//                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
//                    typeface = ResourcesCompat.getFont(requireContext(), R.font.font_pretendard)!!
//                    textColor = ContextCompat.getColor(requireContext(), android.R.color.white)
//                    backgroundImage = ContextCompat.getDrawable(requireContext(), images.getResourceId(position, 0))
//                }
//            }
//        }
//
//        colors.recycle()
//        images.recycle()
//
//        binding.pickerCookie.bubbleSize = 10
//    }

//    private fun setPickerListener() {
//        binding.pickerCookie.listener = object : BubblePickerListener {
//            override fun onBubbleDeselected(item: PickerItem) {
//                Toast.makeText(requireContext(), "${item.title} deselected", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onBubbleSelected(item: PickerItem) {
//                Toast.makeText(requireContext(), "${item.title} selected", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        binding.pickerCookie.onResume()
//    }

//    override fun onPause() {
//        super.onPause()
//        binding.pickerCookie.onPause()
//    }

}