package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.http.RemoteAPIDataService


class ExamDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        tryPullOnlineExams(roomDb)
        return -1
    }

    private fun tryPullOnlineExams(roomDb: RoomDB) {
        try {
            RemoteAPIDataService.apis.getExams().subscribe {
                it?.let {
                    it.filter { onlineExam ->
                        var examDb = roomDb.examSimulation().getById(onlineExam.id)
                        examDb?.let {
                            (onlineExam.version ?: 0) > (it.version ?: 0)
                        } ?: true
                    }.forEach {
                        it.downloaded = false
                        roomDb.examSimulation().insert(it)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun loadOnlineExam(roomDb: RoomDB) {
        RemoteAPIDataService.apis.getExamById(4).subscribe { exam ->
            exam.practiceSectionsDb?.forEach { section ->
                section.templatesDB?.forEach { template ->
                    template.questionsDb?.forEach { question ->
                        question.optionsDb?.forEach {
                            roomDb.practiceAnswerOption().insert(it)
                        }
                        roomDb.practiceQuestion().insert(question)
                    }
                    roomDb.practiceTemplate().insert(template)
                }
                roomDb.practiceSection().insert(section)
            }
            roomDb.examSimulation().insert(exam)
        }
    }
}