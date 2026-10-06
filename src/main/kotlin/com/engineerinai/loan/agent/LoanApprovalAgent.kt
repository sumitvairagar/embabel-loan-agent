package com.engineerinai.loan.agent

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.Ai
import com.embabel.agent.domain.io.UserInput
import com.engineerinai.loan.domain.CreditScore
import com.engineerinai.loan.domain.LoanApplication

/**
 * Loan approval agent — built step by step across the series.
 *
 * EP04: First @Action only — evaluateCredit
 * EP06: Second @Action added — assessRisk
 * EP07: @AchievesGoal added — generateDecision (completes the agent)
 */
@Agent(description = "Processes loan applications and produces a credit decision")
class LoanApprovalAgent {

    /**
     * Action 1: Extract a structured LoanApplication from free text.
     *
     * The LLM reads the user's message and fills in the LoanApplication
     * data class. No parsing code needed — Embabel generates the JSON
     * schema from the data class and instructs the LLM to populate it.
     */
    @Action
    fun extractApplication(userInput: UserInput, ai: Ai): LoanApplication =
        ai.withDefaultLlm().createObject(
            """
            Extract a loan application from this user message.
            Pull out: applicant name, loan amount (in rupees), and purpose.
            
            User message: ${userInput.content}
            """.trimIndent()
        )

    /**
     * Action 2: Evaluate the applicant's credit.
     *
     * Takes LoanApplication from the blackboard (written by extractApplication).
     * The planner sequences these automatically — no ordering code needed.
     */
    @Action
    fun evaluateCredit(application: LoanApplication, ai: Ai): CreditScore =
        ai.withDefaultLlm().createObject(
            """
            Evaluate the credit profile for this loan application.
            
            Applicant: ${application.applicantName}
            Loan amount: ₹${application.amount}
            Purpose: ${application.purpose}
            
            Assign a credit score (300–900), a rating (Excellent/Good/Fair/Poor),
            and list 2-3 key factors that influenced the score.
            """.trimIndent()
        )
}
