package com.lifly.schedule.desktop.logic.dao

import com.lifly.schedule.desktop.logic.model.AppSettings
import com.squareup.moshi.Moshi
import java.io.File

object SettingsSerializer{
    val appSettings:AppSettings
        get() {
            return try{
                println("读取一次")
                getSettings()
            }catch (e:Exception){
                AppSettings()
            }
        }

    private fun getSettings():AppSettings{
        val settingJsonFile = File("app_setting.json")
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(AppSettings::class.java)

        settingJsonFile.createNewFile()

        var json = settingJsonFile.readText()

        if (json==""){
            val defaultJson = jsonAdapter.toJson(AppSettings())
            settingJsonFile.writeText(defaultJson)
            json = defaultJson
        }

        val settings = jsonAdapter.fromJson(json)
        return settings?:AppSettings()
    }


}

fun main(){
    println( SettingsSerializer.appSettings.toString())
}