package com.viastub.kao100.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PracticeSection::class,
        PracticeTemplate::class,
        PracticeQuestion::class,
        PracticeAnswerOption::class,
        DictionaryConfig::class, PracticeTarget::class,
        PracticeBook::class,
        ExamSimulation::class,
        TeachingBook::class,
        TeachingBookUnitSection::class,
        TeachingPoint::class,
        TeachingTranslation::class,
        TeachingBookWord::class,
        GlobalConfigKaoFiltersProvince::class,
        GlobalConfigKaoFiltersType::class,
        MyUser::class,
        MyQuestionAction::class,
        MyQuestionAnsweredHistory::class,
        MyExamSimuHistory::class,
        MySectionPracticeHistory::class,
        MyWordHistory::class
    ],
    version = 16,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun practiceSection(): PracticeSectionDao
    abstract fun practiceTemplate(): PracticeTemplateDao
    abstract fun practiceQuestion(): PracticeQuestionDao
    abstract fun practiceAnswerOption(): PracticeAnswerOptionDao
    abstract fun dictionaryConfig(): DictionaryConfigDao
    abstract fun practiceTarget(): PracticeTargetDao
    abstract fun practiceBook(): PracticeBookDao
    abstract fun examSimulation(): ExamSimulationDao
    abstract fun teachingBook(): TeachingBookDao
    abstract fun teachingBookUnitSection(): TeachingBookUnitSectionDao
    abstract fun teachingTranslation(): TeachingTranslationDao
    abstract fun teachingPoint(): TeachingPointDao
    abstract fun teachingBookWord(): TeachingBookWordDao
    abstract fun globalConfigKaoFiltersProvinces(): GlobalConfigKaoFiltersProvinceDao
    abstract fun globalConfigKaoFiltersTypes(): GlobalConfigKaoFiltersTypeDao
    abstract fun myUser(): MyUserDao
    abstract fun myQuestionAction(): MyQuestionActionDao
    abstract fun myQuestionAnsweredHistory(): MyQuestionAnsweredHistoryDao
    abstract fun myExamSimuHistory(): MyExamSimuHistoryDao
    abstract fun mySectionPracticeHistory(): MySectionPracticeHistoryDao
    abstract fun myWordHistory(): MyWordHistoryDao

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
