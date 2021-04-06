package com.viastub.kao100.db

import androidx.room.*


@Dao
interface PracticeSectionDao {
    @Query("SELECT * FROM PracticeSection")
    fun getAll(): List<PracticeSection>

    @Query("SELECT * FROM PracticeSection where id in (:ids)")
    fun getByIds(ids: MutableList<Int>): List<PracticeSection>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeSection)

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
    fun insert(item: PracticeTemplate)

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
    fun insert(item: PracticeQuestion)

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
    fun insert(item: PracticeAnswerOption)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: MutableList<PracticeAnswerOption>)

    @Delete
    fun delete(item: PracticeAnswerOption)

}


@Dao
interface DictionaryConfigDao {
    @Query("SELECT * FROM DictionaryConfig")
    fun getAll(): List<DictionaryConfig>

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
    fun getAll(): List<PracticeTarget>

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
    fun getAll(): List<PracticeBook>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PracticeBook)

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
interface BookDao {
    @Query("SELECT * FROM Book")
    fun getAll(): List<Book>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Book)

    @Delete
    fun delete(item: Book)
}


@Dao
interface BookAppendixDao {
    @Query("SELECT * FROM BookAppendix")
    fun getAll(): List<BookAppendix>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookAppendix)

    @Delete
    fun delete(item: BookAppendix)
}


@Dao
interface BookUnitDao {
    @Query("SELECT * FROM BookUnit")
    fun getAll(): List<BookUnit>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookUnit)

    @Delete
    fun delete(item: BookUnit)
}


@Dao
interface BookUnitWordsDao {
    @Query("SELECT * FROM BookUnitWords")
    fun getAll(): List<BookUnitWords>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookUnitWords)

    @Delete
    fun delete(item: BookUnitWords)
}


@Dao
interface BookUnitPagesDao {
    @Query("SELECT * FROM BookUnitPages")
    fun getAll(): List<BookUnitPages>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookUnitPages)

    @Delete
    fun delete(item: BookUnitPages)
}


@Dao
interface BookPageDao {
    @Query("SELECT * FROM BookPage")
    fun getAll(): List<BookPage>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookPage)

    @Delete
    fun delete(item: BookPage)
}

@Dao
interface BookTeachingPointDao {
    @Query("SELECT * FROM BookTeachingPoint")
    fun getAll(): List<BookTeachingPoint>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookTeachingPoint)

    @Delete
    fun delete(item: BookTeachingPoint)
}

@Dao
interface BookTranslationDao {
    @Query("SELECT * FROM BookTranslation")
    fun getAll(): List<BookTranslation>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookTranslation)

    @Delete
    fun delete(item: BookTranslation)
}

@Dao
interface BookWordDao {
    @Query("SELECT * FROM BookWord")
    fun getAll(): List<BookWord>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BookWord)

    @Delete
    fun delete(item: BookWord)
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

}

@Dao
interface MyQuestionAnsweredHistoryDao {
    @Query("SELECT * FROM MyQuestionAnsweredHistory")
    fun getAll(): MutableList<MyQuestionAnsweredHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MyQuestionAnsweredHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: MutableList<MyQuestionAnsweredHistory>)

    @Delete
    fun delete(item: MyQuestionAnsweredHistory)

    @Delete
    fun delete(items: MutableList<MyQuestionAnsweredHistory>)
}

