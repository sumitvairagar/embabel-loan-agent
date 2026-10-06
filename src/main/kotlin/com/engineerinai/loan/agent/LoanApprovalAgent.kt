package com.engineerinai.loan.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.Ai
import com.embabel.agent.domain.io.UserInput
import com.engineerinai.loan.domain.CreditScore
import com.engineerinai.loan.domain.LoanApplication
import com.engineerinai.loan.domain.LoanDecision
import com.engineerinai.loan.domain.RiskAssessment

/**
 * Loan approval agent — complete as of EP07.
 *
 * Full flow:
 *   UserInput → extractApplication → LoanApplication
 *   LoanApplication → evaluateCredit → CreditScore
 *   LoanApplication + CreditScore → assessRisk → RiskAssessment
 *   LoanApplication + RiskAssessment → generateDecision → LoanDecision ✓ GOAL
 *
 * The planner sequences all four actions automatically.
 * You wrote zero flow control code.
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

    /**
     * The final action — annotated with @AchievesGoal.
     *
     * When this runs and returns LoanDecision, the agent is complete.
     * The Blackboard now contains LoanDecision and the OODA loop exits.
     *
     * @AchievesGoal creates a Goal whose outputType is LoanDecision.
     * The planner knows: "my job is done when LoanDecision is on the blackboard."
     */
    @AchievesGoal(description = "Produce a final loan decision for the applicant")
    @Action
    fun generateDecision(
        application: LoanApplication,
        assessment: RiskAssessment,
        ai: Ai,
    ): LoanDecision =
        ai.withDefaultLlm().createObject(
            """
            Generate a final loan decision letter.
            
            Applicant: ${application.applicantName}
            Loan amount: ₹${application.amount}
            Purpose: ${application.purpose}
            Risk assessment: ${assessment.riskLevel} risk, approved=${assessment.approved}
            Reason: ${assessment.reason}
            
            Write a clear decision (approved/rejected), a friendly message,
            and 2-3 concrete next steps for the applicant.
            """.trimIndent()
        )
}
