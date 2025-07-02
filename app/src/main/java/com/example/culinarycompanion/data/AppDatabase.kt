package com.example.culinarycompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Recipe::class, User::class, Setting::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun userDao(): UserDao
    abstract fun settingDao(): SettingDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        /** Migration 1→2: create `users` & `settings` tables */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Now uses `full_name` to match @ColumnInfo(name="full_name")
                db.execSQL("""
                  CREATE TABLE IF NOT EXISTS `users` (
                    `id`        INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `username`  TEXT    NOT NULL,
                    `full_name` TEXT    NOT NULL,
                    `email`     TEXT    NOT NULL,
                    `password`  TEXT    NOT NULL
                  )
                """.trimIndent())

                db.execSQL("""
                  CREATE TABLE IF NOT EXISTS `settings` (
                    `id`    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `key`   TEXT    NOT NULL,
                    `value` TEXT    NOT NULL
                  )
                """.trimIndent())
            }
        }

        /** No-op migration 2→3 */
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // no schema changes between v2 and v3
            }
        }

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "culinary_companion_db"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
    }
}