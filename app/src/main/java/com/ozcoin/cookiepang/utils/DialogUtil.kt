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

    fun getDeleteCookieContents(): DialogContents {
        return DialogContents(
            title = "정말 삭제하시겠어요?",
            contents = "쿠키를 삭제하면 다시 복구하기 어려워요.",
            confirm = "삭제하기",
            cancel = "취소하기"
        )
    }

    fun getCloseEditingCookie(): DialogContents {
        return DialogContents(
            title = "쿠키 만들기를 그만 두시겠어요?",
            contents = "이 페이지를 나가면 \n" +
                "만들던 쿠키가 사라져버려요..",
            confirm = "그만두기",
            cancel = "취소하기"
        )
    }

    fun getMakeCookieSuccess(): DialogContents {
        return DialogContents(
            title = "쿠키가 성공적으로 구워졌습니다.",
            contents = "구운 쿠키를 확인하시겠어요?",
            confirm = "확인하러 가기"
        )
    }

    fun getMakeCookieFail(): DialogContents {
        return DialogContents(
            title = "쿠키가 굽기가 실패했습니다.",
            contents = "쿠키 굽기를 알 수 없는 이유로 실패했습니다. \n" +
                "다시 시도하시겠어요?",
            confirm = "다시 시도하기",
            cancel = "취소하기"
        )
    }

    fun getChangeImg(): DialogContents {
        return DialogContents(
            title = "이미지를 변경하시겠어요?",
            contents = "새로 촬영하거나 앨범에서 선택할 수 있어요.",
            confirm = "앨범에서 선택",
            cancel = "새로 촬영"
        )
    }
}
