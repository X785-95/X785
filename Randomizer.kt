package com.example.lab1

import kotlin.random.Random

class Randomizer {
    //генерируем случайное главное число (к которому мы будет определять простоту остальных чисел)
    fun Randomization1(): Int {
        return Random.nextInt(2, 1001)
    }

    //генерируем массив случайных чисел
    fun Randomization2 (): Array<Int>{
        return Array<Int>(5, {Random.nextInt(2,1001)})
    }

    //алгоритм поиска взаимно простых чисел
    fun Easynums (mainnum: Int, masnum: Array<Int>): Array<Int>{
        var mas = Array<Int>(masnum.size, {0})

        for (i in 0..masnum.size-1){
            var a = mainnum
            var b = masnum[i]
            while (Math.max(a,b)%Math.min(a, b) != 0){
                if (Math.max(a,b) == a){ a = Math.max(a,b)%Math.min(a, b)}
                else{b = Math.max(a,b)%Math.min(a, b)}
            }
            if (Math.min(a,b) == 1){mas[i] = masnum[i]}
        }
        return mas
    }

    //Запись массива в строку
    fun formation (masnum: Array<Int>): String{
        var str = ""
        for (i in 0..masnum.size-1){
            if (masnum[i] != 0 && str != ""){
                str = str + "," + masnum[i].toString()
            }else if (masnum[i] != 0 && str == ""){
                str = masnum[i].toString()
            }
            if (masnum[i] == 0){
                str = str + ""
            }
        }
        return str
    }

    //Перепись строки в массив чисел (при ручной записи чисел)
    fun Parser (str: String): Array<Int>{
        val strmas = str.split(",")
        var masint = Array(strmas.size, {strmas[0].toInt()})
        for (i in 0..strmas.size-1){
            masint[i] = strmas[i].toInt()
        }
        return masint
    }
}
