package com.qianli.cixuekaolian.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Section::class, QuestionTemplate::class, Question::class, AnswerOption::class, DictionaryConfig::class, TextBookPractice::class, ExamByTypePractice::class, ExamSimulation::class, TextBook::class, BookAppendix::class, BookUnit::class, UnitWords::class, UnitPages::class, BookPage::class, TeachingPoint::class, Translation::class, BookWord::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun section(): SectionDao
    abstract fun questionTemplate(): QuestionTemplateDao
    abstract fun question(): QuestionDao
    abstract fun answerOption(): AnswerOptionDao
    abstract fun dictionaryConfig(): DictionaryConfigDao
    abstract fun textBookPractice(): TextBookPracticeDao
    abstract fun examByTypePractice(): ExamByTypePracticeDao
    abstract fun examSimulation(): ExamSimulationDao
    abstract fun textBook(): TextBookDao
    abstract fun bookAppendix(): BookAppendixDao
    abstract fun bookUnit(): BookUnitDao
    abstract fun unitWords(): UnitWordsDao
    abstract fun unitPages(): UnitPagesDao
    abstract fun bookPage(): BookPageDao
    abstract fun teachingPoint(): TeachingPointDao
    abstract fun translation(): TranslationDao
    abstract fun bookWord(): BookWordDao

    companion object {
        @Volatile
        private var roomDb: RoomDB? = null

        fun get(applicationContext: Context, dbName: String = "local_db_01"): RoomDB {
            return roomDb ?: (Room.databaseBuilder(
                applicationContext,
                RoomDB::class.java, dbName
            ).fallbackToDestructiveMigration().build().also { roomDb = it })
        }
    }
}
