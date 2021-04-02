package com.viastub.kao100.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PracticeSection::class,
        PracticeQuestionTemplate::class,
        PracticeQuestion::class,
        PracticeAnswerOption::class,
        DictionaryConfig::class, PracticeTarget::class,
        PracticeBook::class,
        ExamSimulation::class,
        Book::class, BookAppendix::class, BookUnit::class, BookUnitWords::class,
        BookUnitPages::class, BookPage::class, BookTeachingPoint::class, BookTranslation::class, BookWord::class,
        GlobalConfigKaoFiltersProvince::class,
        GlobalConfigKaoFiltersType::class],
    version = 2,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun practiceSection(): PracticeSectionDao
    abstract fun practiceQuestionTemplate(): PracticeQuestionTemplateDao
    abstract fun practiceQuestion(): PracticeQuestionDao
    abstract fun practiceAnswerOption(): PracticeAnswerOptionDao
    abstract fun dictionaryConfig(): DictionaryConfigDao
    abstract fun practiceTarget(): PracticeTargetDao
    abstract fun practiceBook(): PracticeBookDao
    abstract fun examSimulation(): ExamSimulationDao
    abstract fun book(): BookDao
    abstract fun bookAppendix(): BookAppendixDao
    abstract fun bookUnit(): BookUnitDao
    abstract fun bookUnitWords(): BookUnitWordsDao
    abstract fun bookUnitPages(): BookUnitPagesDao
    abstract fun bookPage(): BookPageDao
    abstract fun bookTeachingPoint(): BookTeachingPointDao
    abstract fun bookTranslation(): BookTranslationDao
    abstract fun bookWord(): BookWordDao
    abstract fun globalConfigKaoFiltersProvinces(): GlobalConfigKaoFiltersProvinceDao
    abstract fun globalConfigKaoFiltersTypes(): GlobalConfigKaoFiltersTypeDao

    companion object {
        @Volatile
        private var roomDb: RoomDB? = null

        fun get(applicationContext: Context, dbName: String = "local_db_01"): RoomDB {
//            applicationContext.deleteDatabase(dbName)
            return roomDb ?: (Room.databaseBuilder(
                applicationContext,
                RoomDB::class.java, dbName
            ).fallbackToDestructiveMigration().build().also { roomDb = it })
        }

    }
}
