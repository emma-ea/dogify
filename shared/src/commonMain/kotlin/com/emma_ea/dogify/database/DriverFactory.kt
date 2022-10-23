package com.emma_ea.dogify.database

import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope

internal expect fun Scope.createDriver(databaseName: String): SqlDriver