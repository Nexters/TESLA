package com.ozcoin.cookiepang.utils

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.dialog.DialogContents

object DialogUtil {

    fun getDialogContents(cookieDetail: CookieDetail?): DialogContents {
        val dialogContents = DialogContents(
            title = "해당 쿠키를 구매하시겠어요?",
            contents = "이 쿠키를 ${cookieDetail?.hammerPrice}톤에 구매해\n" +
                "쿠키의 내용을 까보시겠어요?",
            confirm = "구매하기",
            cancel = "취소하기"
        )
        return dialogContents
    }
}
