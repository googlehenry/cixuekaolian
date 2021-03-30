package com.qianli.cixuekaolian.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SectionDao {
    @Query("SELECT * FROM Section")
    fun getAll(): List<Section>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: Section)

    @Delete
    fun delete(item: Section)
}

@Dao
interface QuestionTemplateDao {
    @Query("SELECT * FROM QuestionTemplate")
    fun getAll(): List<QuestionTemplate>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: QuestionTemplate)

    @Delete
    fun delete(item: QuestionTemplate)
}


@Dao
interface QuestionDao {
    @Query("SELECT * FROM Question")
    fun getAll(): List<Question>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: Question)

    @Delete
    fun delete(item: Question)
}


@Dao
interface AnswerOptionDao {
    @Query("SELECT * FROM AnswerOption")
    fun getAll(): List<AnswerOption>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: AnswerOption)

    @Delete
    fun delete(item: AnswerOption)
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
interface TextBookPracticeDao {
    @Query("SELECT * FROM TextBookPractice")
    fun getAll(): List<TextBookPractice>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: TextBookPractice)

    @Delete
    fun delete(item: TextBookPractice)
}


@Dao
interface ExamByTypePracticeDao {
    @Query("SELECT * FROM ExamByTypePractice")
    fun getAll(): List<ExamByTypePractice>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: ExamByTypePractice)

    @Delete
    fun delete(item: ExamByTypePractice)
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
interface TextBookDao {
    @Query("SELECT * FROM TextBook")
    fun getAll(): List<TextBook>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: TextBook)

    @Delete
    fun delete(item: TextBook)
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
interface UnitWordsDao {
    @Query("SELECT * FROM UnitWords")
    fun getAll(): List<UnitWords>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: UnitWords)

    @Delete
    fun delete(item: UnitWords)
}


@Dao
interface UnitPagesDao {
    @Query("SELECT * FROM UnitPages")
    fun getAll(): List<UnitPages>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: UnitPages)

    @Delete
    fun delete(item: UnitPages)
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
interface TeachingPointDao {
    @Query("SELECT * FROM TeachingPoint")
    fun getAll(): List<TeachingPoint>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: TeachingPoint)

    @Delete
    fun delete(item: TeachingPoint)
}

@Dao
interface TranslationDao {
    @Query("SELECT * FROM Translation")
    fun getAll(): List<Translation>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
    @Insert
    fun insert(item: Translation)

    @Delete
    fun delete(item: Translation)
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
