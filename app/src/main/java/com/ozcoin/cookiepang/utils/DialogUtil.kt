package com.ozcoin.cookiepang.utils

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.dialog.DialogContents

object DialogUtil {

    fun getConfirmPurchaseCookieContents(cookieDetail: CookieDetail?): DialogContents {
        return DialogContents(
            title = "해당 쿠키를 구매하시겠어요?",
            contents = "이 쿠키를 ${cookieDetail?.hammerPrice}톤에 구매해\n" +
                "쿠키의 내용을 까보시겠어요?",
            confirm = "구매하기",
            cancel = "취소하기"
        )
    }

    fun getPurchaseCookieSuccessContents(): DialogContents {
        return DialogContents(
            title = "쿠키가 성공적으로 구매되었습니다.",
            contents = "구매한 쿠키를 확인하시겠어요?",
            confirm = "확인하러 가기"
        )
    }
}
