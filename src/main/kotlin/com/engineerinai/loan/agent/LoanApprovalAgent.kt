package com.engineerinai.loan.agent

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.Ai
import com.embabel.agent.api.common.create
import com.embabel.agent.domain.io.UserInput
import com.engineerinai.loan.domain.CreditScore
import com.engineerinai.loan.domain.LoanApplication

/**
 * EP04: Two actions only.
 * extractApplication + evaluateCredit.
 * No goal yet — we add that in EP07.
 */
@Agent(description = "Processes loan applications and produces a credit decision")
class LoanApprovalAgent {

    @Action
    fun extractApplication(userInput: UserInput, ai: Ai): LoanApplication =
        ai.withDefaultLlm() create
            """
            Extract a loan application from this user message.
            Pull out: applicant name, loan amount (in rupees), and purpose.
            
            User message: ${userInput.content}
            """.trimIndent()

    @Action
    fun evaluateCredit(application: LoanApplication, ai: Ai): CreditScore =
        ai.withDefaultLlm() create
            """
            Evaluate the credit profile for this loan application.
            
            Applicant: ${application.applicantName}
            Loan amount: ₹${application.amount}
            Purpose: ${application.purpose}
            
            Assign a credit score (300–900), a rating (Excellent/Good/Fair/Poor),
            and list 2-3 key factors that influenced the score.
            """.trimIndent()
}
