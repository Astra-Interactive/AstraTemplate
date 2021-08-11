package com.makeevrserg.empiretemplate.database.entities

import java.sql.ResultSet

/**
 * Sample class with User
 */
data class User(var name: String, var age: Int) {
    constructor(result: ResultSet) : this(result.getString(1),  result.getInt(2))
}