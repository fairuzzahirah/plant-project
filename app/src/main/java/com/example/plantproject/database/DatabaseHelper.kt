package com.example.plantproject.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import com.example.plantproject.model.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // Singleton instance
        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        // Get the singleton instance of the DatabaseHelper
        fun getInstance(context: Context): DatabaseHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseHelper(context.applicationContext).also { INSTANCE = it }
            }
    }

    // Use a database reference that will be kept open
    private var database: SQLiteDatabase? = null

    init {
        // Open the database connection when the helper is initialized
        database = writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USERNAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Insert user into the database
    fun insertUser(user: User) {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
        }

        val result = database?.insert(TABLE_USERS, null, values)
        // Optionally handle result here
    }

    // Check user by email and password
    fun checkUser(email: String, password: String): Boolean {
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor: Cursor = database?.rawQuery(query, arrayOf(email, password)) ?: return false
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Get user by email
    fun getUserByEmail(email: String): User? {
        val cursor = database?.query(
            TABLE_USERS, null,
            "$COLUMN_EMAIL = ?", arrayOf(email),
            null, null, null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val username = it.getString(it.getColumnIndexOrThrow(COLUMN_USERNAME))
                val password = it.getString(it.getColumnIndexOrThrow(COLUMN_PASSWORD))
                return User(username, email, password)
            }
        }
        return null
    }

    // Close the database connection when no longer needed
    fun closeDatabase() {
        database?.close()
    }
}
