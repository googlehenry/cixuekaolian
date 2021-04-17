package com.viastub.kao100.db

import androidx.room.*


@Dao
interface PracticeSectionDao {
    @Query("SELECT * FROM PracticeSection")
    fun getAll(): List<PracticeSection>?

    @Query("SELECT * FROM PracticeSection where id in (:ids)")
    fun getByIds(ids: MutableList<Int>): List<PracticeSection>?

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeSection): Long

    @Delete
    fun delete(item: PracticeSection)

}

@Dao
interface PracticeTemplateDao {
    @Query("SELECT * FROM PracticeTemplate")
    fun getAll(): List<PracticeTemplate>?

    @Query("SELECT * FROM PracticeTemplate where id in (:ids)")
    fun getByIds(ids: MutableList<Int>): List<PracticeTemplate>?

    @Query("SELECT * FROM PracticeTemplate where id=:id")
    fun getById(id: Int): PracticeTemplate?

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeTemplate): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: MutableList<PracticeTemplate>)

    @Delete
    fun delete(item: PracticeTemplate)
}


@Dao
interface PracticeQuestionDao {
    @Query("SELECT * FROM PracticeQuestion")
    fun getAll(): List<PracticeQuestion>

    @Query("SELECT * FROM PracticeQuestion where id in (:ids)")
    fun getByIds(ids: MutableList<Int>): List<PracticeQuestion>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeQuestion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: MutableList<PracticeQuestion>)

    @Delete
    fun delete(item: PracticeQuestion)
}


@Dao
interface PracticeAnswerOptionDao {
    @Query("SELECT * FROM PracticeAnswerOption")
    fun getAll(): List<PracticeAnswerOption>

    @Query("SELECT * FROM PracticeAnswerOption where id in(:ids)")
    fun getByIds(ids: MutableList<Int>): List<PracticeAnswerOption>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeAnswerOption): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: MutableList<PracticeAnswerOption>)

    @Delete
    fun delete(item: PracticeAnswerOption)

}


@Dao
interface DictionaryConfigDao {
    @Query("SELECT * FROM DictionaryConfig")
    fun getAll(): List<DictionaryConfig>

    @Query("SELECT * FROM DictionaryConfig where id=:id")
    fun getById(id: Int): DictionaryConfig?

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DictionaryConfig)

    @Delete
    fun delete(item: DictionaryConfig)
}


@Dao
interface PracticeTargetDao {
    @Query("SELECT * FROM PracticeTarget")
    fun getAll(): List<PracticeTarget>?

    @Query("SELECT * FROM PracticeTarget where name=:name")
    fun getByName(name: String): List<PracticeTarget>?

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeTarget)

    @Delete
    fun delete(item: PracticeTarget)
}


@Dao
interface PracticeBookDao {
    @Query("SELECT * FROM PracticeBook")
    fun getAll(): List<PracticeBook>?

    @Query("SELECT * FROM PracticeBook where id=:id")
    fun getById(id: Int): PracticeBook?

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeBook): Long

    @Delete
    fun delete(item: PracticeBook)
}


@Dao
interface ExamSimulationDao {
    @Query("SELECT * FROM ExamSimulation")
    fun getAll(): List<ExamSimulation>?

    @Query("SELECT * FROM ExamSimulation where province like :province AND testType like :type AND grade like :grade ")
    fun testPapersByFilters(
        province: String, //1=1 when null
        type: String, //1=1 when null
        grade: String // //1=1 when null
    ): List<ExamSimulation>?

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ExamSimulation)

    @Delete
    fun delete(item: ExamSimulation)
}


@Dao
interface TeachingBookDao {
    @Query("SELECT * FROM TeachingBook order by grade desc")
    fun getAll(): List<TeachingBook>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TeachingBook)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<TeachingBook>)

    @Delete
    fun delete(item: TeachingBook)
}


@Dao
interface TeachingBookUnitSectionDao {
    @Query("SELECT * FROM TeachingBookUnitSection")
    fun getAll(): List<TeachingBookUnitSection>?

    @Query("SELECT * FROM TeachingBookUnitSection where id in(:ids)")
    fun getByIds(ids: MutableList<Int>): List<TeachingBookUnitSection>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TeachingBookUnitSection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<TeachingBookUnitSection>)

    @Delete
    fun delete(item: TeachingBookUnitSection)
}


@Dao
interface TeachingPointDao {
    @Query("SELECT * FROM TeachingPoint")
    fun getAll(): List<TeachingPoint>

    @Query("SELECT * FROM TeachingPoint where id in(:ids)")
    fun getByIds(ids: MutableList<Int>): List<TeachingPoint>?

    @Query("SELECT * FROM TeachingPoint where id =:id")
    fun getById(id: Int): TeachingPoint?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TeachingPoint)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<TeachingPoint>)

    @Delete
    fun delete(item: TeachingPoint)
}


@Dao
interface TeachingTranslationDao {
    @Query("SELECT * FROM TeachingTranslation")
    fun getAll(): List<TeachingTranslation>

    @Query("SELECT * FROM TeachingTranslation where id in(:ids)")
    fun getByIds(ids: MutableList<Int>): List<TeachingTranslation>?

    @Query("SELECT * FROM TeachingTranslation where id =:id")
    fun getById(id: Int): TeachingTranslation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TeachingTranslation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<TeachingTranslation>)

    @Delete
    fun delete(item: TeachingTranslation)
}


@Dao
interface TeachingBookWordDao {
    @Query("SELECT * FROM TeachingBookWord")
    fun getAll(): List<TeachingBookWord>?

    @Query("SELECT * FROM TeachingBookWord where id in(:ids)")
    fun getByIds(ids: MutableList<Int>): List<TeachingBookWord>?

    @Query("SELECT * FROM TeachingBookWord where id =:id")
    fun getById(id: Int): TeachingBookWord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TeachingBookWord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<TeachingBookWord>)

    @Delete
    fun delete(item: TeachingBookWord)
}

@Dao
interface GlobalConfigKaoFiltersProvinceDao {
    @Query("SELECT * FROM GlobalConfigKaoFiltersProvince")
    fun getAll(): MutableList<GlobalConfigKaoFiltersProvince>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: GlobalConfigKaoFiltersProvince)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<GlobalConfigKaoFiltersProvince>)

    @Delete
    fun delete(item: GlobalConfigKaoFiltersProvince)

    @Delete
    fun delete(items: MutableList<GlobalConfigKaoFiltersProvince>)
}

@Dao
interface GlobalConfigKaoFiltersTypeDao {
    @Query("SELECT * FROM GlobalConfigKaoFiltersType")
    fun getAll(): MutableList<GlobalConfigKaoFiltersType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: GlobalConfigKaoFiltersType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<GlobalConfigKaoFiltersType>)

    @Delete
    fun delete(item: GlobalConfigKaoFiltersType)

    @Delete
    fun delete(items: MutableList<GlobalConfigKaoFiltersType>)
}

@Dao
interface MyUserDao {
    @Query("SELECT * FROM MyUser")
    fun getAll(): MutableList<MyUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MyUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<MyUser>)

    @Delete
    fun delete(item: MyUser)

    @Delete
    fun delete(items: MutableList<MyUser>)
}

@Dao
interface MyQuestionActionDao {
    @Query("SELECT * FROM MyQuestionAction")
    fun getAll(): MutableList<MyQuestionAction>

    @Query("SELECT * FROM MyQuestionAction where userId=:userId and practiceQuestionId=:questionId limit 1")
    fun getByQuestionIdsOfUser(userId: Int, questionId: Int): MyQuestionAction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MyQuestionAction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: MutableList<MyQuestionAction>)

    @Delete
    fun delete(item: MyQuestionAction)

    @Delete
    fun delete(items: MutableList<MyQuestionAction>)

    @Query("DELETE FROM MyQuestionAction")
    fun deleteAll()
}

@Dao
interface MyQuestionAnsweredHistoryDao {
    @Query("SELECT * FROM MyQuestionAnsweredHistory")
    fun getAll(): MutableList<MyQuestionAnsweredHistory>

    @Query("SELECT * FROM MyQuestionAnsweredHistory where userId=:userId and practiceQuestionId=:questionId order by id desc limit 1")
    fun getByUserIdOfAnsweredHistory(userId: Int, questionId: Int): MyQuestionAnsweredHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MyQuestionAnsweredHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: MutableList<MyQuestionAnsweredHistory>)

    @Delete
    fun delete(item: MyQuestionAnsweredHistory)

    @Delete
    fun delete(items: MutableList<MyQuestionAnsweredHistory>)

    @Query("DELETE FROM MyQuestionAnsweredHistory")
    fun deleteAll()
}

@Dao
interface MyExamSimuHistoryDao {
    @Query("SELECT * FROM MyExamSimuHistory")
    fun getAll(): MutableList<MyExamSimuHistory>

    @Query("SELECT * FROM MyExamSimuHistory where userId=:userId and examSimulationId=:examId order by id desc limit 1")
    fun getByUserIdOfExam(userId: Int, examId: Int): MyExamSimuHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MyExamSimuHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: MutableList<MyExamSimuHistory>)

    @Delete
    fun delete(item: MyExamSimuHistory)

    @Delete
    fun delete(items: MutableList<MyExamSimuHistory>)

    @Query("DELETE FROM MyExamSimuHistory")
    fun deleteAll()
}

@Dao
interface MySectionPracticeHistoryDao {
    @Query("SELECT * FROM MySectionPracticeHistory")
    fun getAll(): MutableList<MySectionPracticeHistory>

    @Query("SELECT * FROM MySectionPracticeHistory where userId=:userId and sectionId=:sectionId order by id desc limit 1")
    fun getByUserIdAndSectionId(userId: Int, sectionId: Int): MySectionPracticeHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MySectionPracticeHistory): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: MutableList<MySectionPracticeHistory>)

    @Delete
    fun delete(item: MySectionPracticeHistory)

    @Delete
    fun delete(items: MutableList<MySectionPracticeHistory>)

    @Query("DELETE FROM MySectionPracticeHistory")
    fun deleteAll()
}

@Dao
interface MyWordHistoryDao {
    @Query("SELECT * FROM MyWordHistory")
    fun getAll(): MutableList<MyWordHistory>

    @Query("SELECT * FROM MyWordHistory where userId=:userId and word=:word order by id desc limit 1")
    fun getByUserIdAndWord(userId: Int, word: String): MyWordHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MyWordHistory): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: MutableList<MyWordHistory>)

    @Delete
    fun delete(item: MyWordHistory)

    @Delete
    fun delete(items: MutableList<MyWordHistory>)

    @Query("DELETE FROM MyWordHistory")
    fun deleteAll()
}

