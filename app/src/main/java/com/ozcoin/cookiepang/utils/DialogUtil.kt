package com.ozcoin.cookiepang.utils

import com.ozcoin.cookiepang.common.URL_OFTEN_ASK_QUESTIONS
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.dialog.DialogContents
import com.ozcoin.cookiepang.domain.dialog.DialogLink

object DialogUtil {

    fun getConfirmPurchaseCookieContents(cookieDetail: CookieDetail?): DialogContents {
        return DialogContents(
            title = "이 쿠키를 구매하시겠어요?",
            contents = "망치 ${cookieDetail?.hammerPrice}톤에 구매해 쿠키의 답변을 열 수 있어요.",
            confirm = "구매하기",
            cancel = "취소하기"
        )
    }

    fun getPurchaseCookieSuccessContents(): DialogContents {
        return DialogContents(
            title = "쿠키가 성공적으로 구매되었습니다.",
            contents = "구매한 쿠키의 답변을 확인하시겠어요?",
            confirm = "확인하러 가기"
        )
    }

    fun getPurchaseCookieFailContents(): DialogContents {
        return DialogContents(
            title = "쿠키가 구매가 실패했습니다.",
            contents = "쿠키 구매를 실패했어요.\n다시 시도하시겠어요?",
            confirm = "다시 시도하기",
            cancel = "취소하기"
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

    fun getCloseEditingCookieContetns(): DialogContents {
        return DialogContents(
            title = "쿠키 굽기를 중단하시겠어요?",
            contents = "이 페이지를 나가면 \n" +
                "만들던 쿠키가 사라져요.",
            confirm = "그만두기",
            cancel = "취소하기"
        )
    }

    fun getMakeCookiePreAlertContents(): DialogContents {
        return DialogContents(
            title = "쿠키를 구우시겠어요?",
            contents = "쿠키를 구우려면 망치 5톤이 필요해요.",
            confirm = "쿠키 굽기",
            cancel = "취소하기"
        )
    }

    fun getMakeCookieSuccessContents(): DialogContents {
        return DialogContents(
            title = "쿠키가 성공적으로 구워졌어요",
            contents = "답변이 안전하게 쿠키 안에 보관되었어요.",
            confirm = "확인하러 가기",
            cancel = "피드로 돌아가기"
        )
    }

    fun getMakeCookieFailContents(): DialogContents {
        return DialogContents(
            title = "쿠키가 굽기가 실패했습니다.",
            contents = "쿠키 굽기를 알 수 없는 이유로 실패했습니다. \n" +
                "다시 시도하시겠어요?",
            confirm = "다시 시도하기",
            cancel = "취소하기"
        )
    }

    fun getChangeImgContents(): DialogContents {
        return DialogContents(
            title = "이미지를 변경하시겠어요?",
            contents = "새로 촬영하거나 앨범에서 선택할 수 있어요.",
            confirm = "앨범에서 선택",
            cancel = "새로 촬영"
        )
    }

    fun getNotEnoughHammerContents(): DialogContents {
        return DialogContents(
            title = "망치가 모자라요",
            contents = "3 클레이튼으로 망치 3톤을 환전하시겠어요?",
            confirm = "진행하기",
            cancel = "취소하기"
        )
    }

    fun getNotEnoughKlayContents(): DialogContents {
        return DialogContents(
            title = "보유한 클레이튼이 부족합니다",
            contents = "클레이튼이 부족해 망치로 환전하지 못했어요.\n클레이튼을 충전해주세요.",
            confirm = "확인하기"
        )
    }

    fun getIsHiddenCookieContents(): DialogContents {
        return DialogContents(
            title = "이 쿠키는 숨겨졌어요..!",
            contents = "숨겨진 쿠키는 소유자가 공개할 때까지\n볼 수 없어요. 다른 쿠키를 구경해볼까요?",
            confirm = "확인하기"
        )
    }

    fun getWalletApproveRequiredContents(): DialogContents {
        return DialogContents(
            title = "지갑 권한 허가가 필요합니다.",
            contents = "쿠키 거래를 위해서 최초 1회\n지갑 권한 허가가 필요해요.",
            confirm = "권한 허가하기",
            cancel = "취소하기",
            dialogLink = DialogLink("지갑 권한 허가란?", URL_OFTEN_ASK_QUESTIONS)
        )
    }

    fun getIsWalletApproveSuccessContents(): DialogContents {
        return DialogContents(
            title = "권한 설정이 완료되었습니다",
            contents = "자유롭게 쿠키를 사고 팔아 보세요",
            confirm = "닫기"
        )
    }

    fun getCheckKlipLogOutContents(): DialogContents {
        return DialogContents(
            title = "Klip 연동을 해제하시겠어요?",
            contents = "지갑 연동을 해제하면 다시 로그인 해야해요.",
            confirm = "재시도하기",
            cancel = "취소 하기"
        )
    }
}
