package com.ozcoin.cookiepang.utils

import com.ozcoin.cookiepang.domain.alarm.Alarm
import com.ozcoin.cookiepang.domain.alarm.Alarms
import com.ozcoin.cookiepang.domain.cookie.Cookie
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistoryType
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import com.ozcoin.cookiepang.domain.feed.Feed
import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryColorStyle
import com.ozcoin.cookiepang.domain.userinfo.UserInfo
import java.util.Random

object DummyUtil {

    fun getUserCategoryList() = DataResult.OnSuccess(
        listOf(
            UserCategory("Free Chat", false, UserCategoryColorStyle.PINK),
            UserCategory("Money", false, UserCategoryColorStyle.BLUE),
            UserCategory("Friend", false, UserCategoryColorStyle.ORANGE),
            UserCategory("Hobby", false, UserCategoryColorStyle.PURPLE),
            UserCategory("Coffee", false, UserCategoryColorStyle.ORANGE),
            UserCategory("Hiphop", false, UserCategoryColorStyle.BLUE),
            UserCategory("blah blah blah blah", false, UserCategoryColorStyle.ORANGE),
            UserCategory("Coin", false)
        )
    )

    fun getFeedList(): DataResult.OnSuccess<List<Feed>> {
        val list = mutableListOf<Feed>()
        val random = Random()

        repeat(100) {
            val feed = Feed(
                isHidden = it % 8 != 0,
                userThumbnailUrl = "",
                userProfileId = "Say My Name#$it",
                createdTimeStamp = "$it min ago",
                question = "My real Secret is too many speaker(#$it)",
                answer = "오늘도 이 비는 그치지 않아. 모두 어디서 흘러 오는 건지. 창 밖으로 출렁이던 헤드라잇 강물도. 갈 곳을 잃은채 울먹이고, 자동응답기의 공허한 시간. 모두 어디로 흘러가는 건지. 기다림은 방 한 구석 잊혀진 화초처럼. 조금씩 시들어 고개 숙여가고.",
                cookieCardStyle = getCookieCardStyle(),
                viewCount = it * (random.nextInt(291) + 1),
                hammerPrice = it * (random.nextInt(8) + 1),
                cookieId = it,
                feedUserId = it.toString()
            )
            list.add(feed)
        }

        return DataResult.OnSuccess(list.toList())
    }

    fun getCookieCardStyle(): CookieCardStyle {
        val random = Random().nextInt(3) + 1
        return if (random % 3 == 0) CookieCardStyle.BLUE else if (random % 3 == 1) CookieCardStyle.YELLOW else CookieCardStyle.PINK
    }

    fun getCookieDetail(isMine: Boolean, isHidden: Boolean): DataResult<CookieDetail> {
        return DataResult.OnSuccess(
            CookieDetail(
                isMine = isMine,
                userCategory = UserCategory("Free Chat", true),
                viewCount = 423,
                question = "나에게 가장 수치스러운 것은?",
                cookieCardStyle = getCookieCardStyle(),
                answer = "아몰랑 없어 ㅎ3ㅎ\n" + ".　｡・｡∧_∧｡・｡\n" + "｡ﾟ 　( ﾟ´Д｀)　 ﾟ｡\n" + "　 　o( U U\n" + "　　　 'ｰ'ｰ'\n",
                hammerPrice = 24,
                collectorUserId = "11",
                collectorThumbnailUrl = "",
                collectorName = "강동구 호랑이3333333",
                creatorUserId = "12",
                creatorName = "상일동 치타",
                creatorThumbnailUrl = "",
                contractAddress = "0xesf232424124214124214",
                cookieHistory = listOf(
                    CookieHistory(
                        CookieHistoryType.PURCHASE,
                        "‘강동구 호랑이' 님이 ‘Q.내가 여자친구가 있을까'를 망치 32개로 수정했습니다.",
                        "2022-02-22 20:00"
                    ),
                    CookieHistory(
                        CookieHistoryType.MODIFY,
                        "‘강동구 호랑이' 님이 ‘Q.내가 여자친구가 있을까'를 망치 34개로 깠습니다.",
                        "2022-10-32 20:00"
                    ),
                    CookieHistory(
                        CookieHistoryType.CREATE,
                        "‘상일동 치타' 님이 ‘Q.내가 여자친구가 있을까'를 망치 34개로 만들었습니다.",
                        "2022-10-32 20:00"
                    )
                ),
                isHidden = isHidden,
                isOnSale = true,
                nftTokenId = 10,
                cookieId = 10
            )
        )
    }

    fun getAlarmsList(): DataResult.OnSuccess<List<Alarms>> {
        val list = mutableListOf<Alarms>()

        repeat(10) {
            val alarms = Alarms(
                "2022-01-$it",
                getAlarmList()
            )
            list.add(alarms)
        }

        return DataResult.OnSuccess(list)
    }

    fun getAlarmList(): List<Alarm> {
        val list = mutableListOf<Alarm>()

        repeat(4) {
            val alarm = Alarm(
                it, "Ask", "Questions($it)", "21:01"
            )
            list.add(alarm)
        }

        return list
    }

    fun getLoginUser(): User {
        return User().apply {
            profileID = ""
            userId = "user"
        }
    }

    fun getUserInfo(isMine: Boolean): DataResult<UserInfo> {
        return DataResult.OnSuccess(
            UserInfo(
                isMine = isMine,
                userId = "user",
                profileId = "Hi im back",
                introduce = "jooooooon buuuuu",
                thumbnailUrl = null,
                profileBackgroundImgUrl = null,
                collectedCnt = 10,
                createdCnt = 291,
                questionCnt = 291
            )
        )
    }

    fun getQuestionList(): DataResult<List<Question>> {
        val list = mutableListOf<Question>()

        repeat(22) {
            val question = Question(
                it,
                "(#$it)이거어슨 무슨 질문을 하기 위한 질문 ? ",
                UserCategory.typeAll().categoryId,
                it % 3 == 0
            )
            list.add(question)
        }

        return DataResult.OnSuccess(list.toList())
    }

    fun getCollectedCookieList(): DataResult<List<Cookie>> {
        val list = mutableListOf<Cookie>()

        repeat(22) {
            val cookie = Cookie(
                cookieId = "cookieId($it)",
                isHidden = it % 2 == 0,
                cookieBoxCoverImgUrl = "",
                cookieBoxImgUrl = "",
                cookieImgUrl = "",
                cookieCardStyle = CookieCardStyle.BLUE,
                ownedUserId = ""
            )
            list.add(cookie)
        }

        return DataResult.OnSuccess(list.toList())
    }

    fun getCreatedCookieList(): DataResult<List<Cookie>> {
        val list = mutableListOf<Cookie>()

        repeat(12) {
            val cookie = Cookie(
                cookieId = "cookieId($it)",
                isHidden = it % 5 == 0,
                cookieBoxCoverImgUrl = "",
                cookieBoxImgUrl = "",
                cookieImgUrl = "",
                cookieCardStyle = CookieCardStyle.BLUE,
                ownedUserId = ""
            )
            list.add(cookie)
        }

        return DataResult.OnSuccess(list.toList())
    }
}
