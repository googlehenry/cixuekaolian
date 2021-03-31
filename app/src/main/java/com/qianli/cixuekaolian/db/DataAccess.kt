package com.qianli.cixuekaolian.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PracticeSectionDao {
    @Query("SELECT * FROM PracticeSection")
    fun getAll(): List<PracticeSection>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: PracticeSection)

    @Delete
    fun delete(item: PracticeSection)
}

@Dao
interface PracticeQuestionTemplateDao {
    @Query("SELECT * FROM PracticeQuestionTemplate")
    fun getAll(): List<PracticeQuestionTemplate>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: PracticeQuestionTemplate)

    @Delete
    fun delete(item: PracticeQuestionTemplate)
}


@Dao
interface PracticeQuestionDao {
    @Query("SELECT * FROM PracticeQuestion")
    fun getAll(): List<PracticeQuestion>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: PracticeQuestion)

    @Delete
    fun delete(item: PracticeQuestion)
}


@Dao
interface PracticeAnswerOptionDao {
    @Query("SELECT * FROM PracticeAnswerOption")
    fun getAll(): List<PracticeAnswerOption>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: PracticeAnswerOption)

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
    @Insert
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
    @Insert
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
    @Insert
    fun insert(item: PracticeBook)

    @Delete
    fun delete(item: PracticeBook)
}


@Dao
interface ExamSimulationDao {
    @Query("SELECT * FROM ExamSimulation")
    fun getAll(): List<ExamSimulation>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
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
    @Insert
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
    @Insert
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
    @Insert
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
    @Insert
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
    @Insert
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
    @Insert
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
    @Insert
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
    @Insert
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
    @Insert
    fun insert(item: BookWord)

    @Delete
    fun delete(item: BookWord)
}
