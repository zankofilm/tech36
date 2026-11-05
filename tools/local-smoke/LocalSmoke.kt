package ir.madreseyar.teacher

import org.json.JSONObject

fun main() {
    val contacts = listOf(
        JSONObject().put("studentId", "a").put("subject", "علوم"),
        JSONObject().put("studentId", "b").put("subject", "ریاضی"))
    check(findContactForTarget(contacts, MessageTarget("a", "علوم")) != null)
    check(findContactForTarget(contacts, MessageTarget("a", "ریاضی")) == null)
    check(findContactForTarget(contacts, MessageTarget("c", "علوم")) == null)
    check(findContactForTarget(contacts, MessageTarget("", "علوم")) == null)
    val good = QuestionReviewMeta(text="کدام درست است؟",type="mcq",score=1.0,
        choices=listOf("الف","ب","ج","د"),answerIndex=2)
    check(questionReviewIssue(good).isEmpty())
    check(questionReviewIssue(good.copy(answerIndex=null)).contains("کلید"))
    check(questionReviewIssue(good.copy(choices=listOf("الف", "الف", "ج", "د"))).contains("تکراری"))
    check(bankQuestionMatchesSearch("كتاب",listOf("کتاب علوم")))
    check(!bankQuestionMatchesSearch("ریاضی",listOf("کتاب علوم")))
    check(ApiPath.resolve("https://school.example/app/", "/api/staff/me").toString() == "https://school.example/app/api/staff/me")
    val rejected = listOf("/api/../admin", "https://example.com/evil", "/admin")
    rejected.forEach { path -> check(runCatching { ApiPath.resolve("https://school.example/app/", path) }.isFailure) }
    println("PASS 13 local assertions: message scope, question review, bank search, API URL guard")
}
