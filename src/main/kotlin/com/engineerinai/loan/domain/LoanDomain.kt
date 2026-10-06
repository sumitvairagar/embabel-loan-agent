package com.engineerinai.loan.domain

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonPropertyDescription

/**
 * The starting input — what the user submits.
 * This is what goes onto the Blackboard first.
 */
@JsonClassDescription("A loan application submitted by a customer")
data class LoanApplication(
    @JsonPropertyDescription("Full name of the applicant")
    val applicantName: String,

    @JsonPropertyDescription("Loan amount requested in rupees")
    val amount: Int,

    @JsonPropertyDescription("Purpose of the loan: home, car, personal, business")
    val purpose: String,
)

/**
 * Produced by the credit check action.
 * Written to the Blackboard after evaluateCredit runs.
 */
@JsonClassDescription("Credit evaluation result for a loan applicant")
data class CreditScore(
    @JsonPropertyDescription("Numeric credit score between 300 and 900")
    val score: Int,

    @JsonPropertyDescription("Rating: Excellent, Good, Fair, or Poor")
    val rating: String,

    @JsonPropertyDescription("Key factors that influenced the score")
    val factors: List<String>,
)

/**
 * Produced by the risk assessment action.
 * Written to the Blackboard after assessRisk runs.
 */
@JsonClassDescription("Risk assessment for a loan application")
data class RiskAssessment(
    val approved: Boolean,
    val riskLevel: String,   // Low, Medium, High
    val reason: String,
)

/**
 * Produced by the final decision action.
 * This is the Goal — when this hits the Blackboard, the agent is done.
 */
@JsonClassDescription("Final loan decision")
data class LoanDecision(
    val approved: Boolean,
    val message: String,
    val nextSteps: List<String>,
)
