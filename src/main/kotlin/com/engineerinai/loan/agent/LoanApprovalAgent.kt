package com.engineerinai.loan.agent

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.Ai
import com.embabel.agent.domain.io.UserInput
import com.engineerinai.loan.domain.CreditScore
import com.engineerinai.loan.domain.LoanApplication
import com.engineerinai.loan.domain.RiskAssessment

/**
 * Loan approval agent — built step by step across the series.
 *
 * EP04: extractApplication + evaluateCredit
 * EP06: assessRisk added — planner now chains all three automatically
 * EP07: @AchievesGoal added — generateDecision (completes the agent)
 */
@Agent(description = "Processes loan applications and produces a credit decision")
class LoanApprovalAgent {

    @Action
    fun extractApplication(userInput: UserInput, ai: Ai): LoanApplication =
        ai.withDefaultLlm().createObject(
            """
            Extract a loan application from this user message.
            Pull out: applicant name, loan amount (in rupees), and purpose.
            
            User message: ${userInput.content}
            """.trimIndent()
        )

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

    /**
     * Action 3: Assess the risk of approving this loan.
     *
     * Takes BOTH LoanApplication and CreditScore from the blackboard.
     * The planner ensures both are available before running this action —
     * extractApplication and evaluateCredit must run first.
     *
     * You wrote zero ordering code. The planner inferred: 
     * extractApplication → evaluateCredit → assessRisk
     */
    @Action
    fun assessRisk(
        application: LoanApplication,
        score: CreditScore,
        ai: Ai,
    ): RiskAssessment =
        ai.withDefaultLlm().createObject(
            """
            Assess the risk of approving this loan.
            
            Applicant: ${application.applicantName}
            Loan amount: ₹${application.amount}
            Purpose: ${application.purpose}
            Credit score: ${score.score} (${score.rating})
            Credit factors: ${score.factors.joinToString(", ")}
            
            Determine: approved (true/false), risk level (Low/Medium/High),
            and the main reason for the decision.
            """.trimIndent()
        )
}
