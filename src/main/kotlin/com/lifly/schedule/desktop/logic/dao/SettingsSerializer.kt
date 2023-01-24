package com.lifly.schedule.desktop.logic.dao

import com.lifly.schedule.desktop.logic.model.AppSettings
import com.squareup.moshi.Moshi
import java.io.File

object SettingsSerializer{
    private val path = System.getProperty("user.home")+"\\.scheduleDesktopData"
    val appSettings:AppSettings
        get() {
            return try{
                getSettings()
            }catch (e:Exception){
                AppSettings()
            }
        }

    private fun getSettings():AppSettings{
        val settingJsonFile = File("$path\\app_setting.json")
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