package com.appinventiv.mvvmunittesting

import java.io.InputStreamReader
import java.lang.StringBuilder

object Util {
 fun readResourceFile(fileName: String) : String{
     val inputStream = Util::class.java.getResourceAsStream(fileName)
     val builder = StringBuilder()
     val reader = InputStreamReader(inputStream, "UTF-8")
     reader.readLines().forEach {
         builder.append(it)
     }
     return builder.toString()
 }
}