package com.vladyslavkhimich.countouch.model.interfaces

import com.vladyslavkhimich.countouch.database.entity.Tag

interface TemplateStrategy {
    fun getName(): String
    fun getInitialCount(): Int
    fun getStep(): Int
    fun getTag(): Tag
}