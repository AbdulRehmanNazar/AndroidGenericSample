package com.ar.sample.interfaces

/**
 * @Author: Abdul Rehman
 */
interface ItemClickListener<T> {
    fun onItemClick(item: T, type: String)
}