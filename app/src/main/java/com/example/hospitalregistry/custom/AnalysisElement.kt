package com.example.hospitalregistry.custom

class AnalysisElement(private var title: String, private var value: String) {


    public fun setTitle(title: String) {
        this.title = title
    }

    public fun setValue(value: String) {
        this.value = value
    }

    public fun getTitle(): String {
        return title
    }

    public fun getValue(): String {
        return value
    }
}
