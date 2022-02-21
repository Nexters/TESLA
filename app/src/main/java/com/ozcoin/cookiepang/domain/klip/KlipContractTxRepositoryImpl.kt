package com.ozcoin.cookiepang.domain.klip

import android.content.Context
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.data.klip.KlipContractTxDataSource
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.user.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class KlipContractTxRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository,
    private val klipContractTxDataSource: KlipContractTxDataSource
) : KlipContractTxRepository {

    companion object {
        private const val CONTRACT_TYPE_COOKIE = 810
        private const val CONTRACT_TYPE_HAMMER = 811
    }

    override suspend fun requestMakeACookie(editCookie: EditCookie): Boolean =
        withContext(Dispatchers.IO) {
            val response = CompletableDeferred<Boolean>()
            val to = getContractAddress(CONTRACT_TYPE_COOKIE) ?: ""
            val from = userRepository.getLoginUser()?.walletAddress ?: ""
            val value = "0"
            val abi = getContractFunc(CONTRACT_TYPE_COOKIE, "mintCookieByHammer")
            val params = editCookie.toMintCookieByHammer().toKlipParams()

            klipContractTxDataSource.prepareRequest(to, from, value, abi, params) {
                response.complete(it)
            }

            val result = response.await()
            if (result) {
                withContext(Dispatchers.Main) {
                    klipContractTxDataSource.request()
                }
            }
            result
        }

    override suspend fun requestBuyACookie(cookieDetail: CookieDetail): Boolean =
        withContext(Dispatchers.IO) {
            val response = CompletableDeferred<Boolean>()
            val to = getContractAddress(CONTRACT_TYPE_COOKIE) ?: ""
            val from = userRepository.getLoginUser()?.walletAddress ?: ""
            val value = "0"
            val abi = getContractFunc(CONTRACT_TYPE_COOKIE, "buyCookie")
            val params = ArrayList<Any>().apply {
                add(cookieDetail.cookieId.toString())
            }

            klipContractTxDataSource.prepareRequest(to, from, value, abi, params) {
                response.complete(it)
            }
            val result = response.await()
            if (result) {
                withContext(Dispatchers.Main) {
                    klipContractTxDataSource.request()
                }
            }
            result
        }

    override suspend fun approveWallet(approve: Boolean): Boolean = withContext(Dispatchers.IO) {
        val response = CompletableDeferred<Boolean>()
        val to = getContractAddress(CONTRACT_TYPE_HAMMER) ?: ""
        val from = userRepository.getLoginUser()?.walletAddress ?: ""
        val value = "0"
        val abi = getContractFunc(CONTRACT_TYPE_HAMMER, "maxApprove")
        val params = ArrayList<Any>()

        klipContractTxDataSource.prepareRequest(to, from, value, abi, params) {
            response.complete(it)
        }

        val result = response.await()
        if (result) {
            withContext(Dispatchers.Main) {
                klipContractTxDataSource.request()
            }
        }
        result
    }

    override fun getResult(callback: (Boolean, String?) -> Unit) {
        klipContractTxDataSource.getResult(callback)
    }

    private fun readRawTxt(id: Int): String? {
        var data: String? = null
        val inputStream: InputStream = context.resources.openRawResource(id)
        val byteArrayOutputStream = ByteArrayOutputStream()
        var i: Int
        try {
            i = inputStream.read()
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = inputStream.read()
            }
            data = byteArrayOutputStream.toString("MS949")
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }

    private fun getContractAddress(contractType: Int): String? {
        return kotlin.runCatching {
            val temp = readRawTxt(R.raw.contract_address)
            val jsonObject = JSONObject(temp!!)
            if (contractType == CONTRACT_TYPE_COOKIE)
                jsonObject.getString("CookieContract")
            else
                jsonObject.getString("HammerContract")
        }.onFailure {
            Timber.d(it)
        }.getOrNull()
    }

    private fun getContractFunc(cookieType: Int, funcName: String): String {
        return if (cookieType == CONTRACT_TYPE_COOKIE) {
            getCookieContractFunc(funcName)
        } else {
            getHammerContractFunc(funcName)
        }
    }

    private fun getCookieContractFunc(funcName: String): String {
        var result = ""
        try {
            val jsonArray = JSONArray(readRawTxt(R.raw.abi_cookie))
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                if (obj.has("name")) {
                    if (obj["name"] == funcName) {
                        result = obj.toString()
                        break
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return result
    }

    private fun getHammerContractFunc(funcName: String): String {
        var result = ""
        try {
            val jsonArray = JSONArray(readRawTxt(R.raw.abi_hammer))
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                if (obj.has("name")) {
                    if (obj["name"] == funcName) {
                        result = obj.toString()
                        break
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return result
    }
}
