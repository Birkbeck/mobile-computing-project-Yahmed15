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
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Migration from version 1 to 2.
         * Creates `users` and `settings` tables.
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `users` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `username` TEXT NOT NULL,
                        `full_name` TEXT NOT NULL,
                        `email` TEXT NOT NULL,
                        `password` TEXT NOT NULL
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `settings` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `key` TEXT NOT NULL,
                        `value` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        /**
         * Migration from version 2 to 3.
         * No schema changes but maintained for version consistency.
         */
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // No changes in schema
            }
        }

        /**
         * Returns the singleton instance of the database.
         */
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        /**
         * Used in ViewModel or other layers to get the DB instance.
         */
        fun getDatabase(context: Context): AppDatabase = getInstance(context)

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "culinary_companion_db"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
    }
}