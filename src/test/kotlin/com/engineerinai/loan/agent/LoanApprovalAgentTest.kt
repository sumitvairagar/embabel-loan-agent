package com.engineerinai.loan.agent

import com.embabel.agent.testing.unit.FakeOperationContext
import com.engineerinai.loan.domain.CreditScore
import com.engineerinai.loan.domain.LoanApplication
import com.engineerinai.loan.domain.LoanDecision
import com.engineerinai.loan.domain.RiskAssessment
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * EP08: Unit testing the loan agent.
 *
 * Key principle: we test the PROMPT content and LOGIC,
 * not the LLM's response. The LLM is faked out entirely.
 *
 * FakeOperationContext lets you:
 * - Inject a fake Ai that captures all LLM invocations
 * - Assert that prompts contain the right data
 * - Assert that the right hyperparameters were used
 * - No real API calls, no cost, no network needed
 */
class LoanApprovalAgentTest {

    private val agent = LoanApprovalAgent()

    @Nested
    inner class `evaluateCredit prompt` {

        @Test
        fun `must contain applicant name`() {
            val context = FakeOperationContext()
            context.expectResponse(CreditScore(750, "Good", listOf("stable income")))

            val application = LoanApplication("Sumit Vairagar", 500000, "home")
            agent.evaluateCredit(application, context)

            val prompt = context.llmInvocations.first().prompt
            assertTrue(prompt.contains("Sumit Vairagar")) {
                "Expected prompt to contain applicant name but was: $prompt"
            }
        }

        @Test
        fun `must contain loan amount`() {
            val context = FakeOperationContext()
            context.expectResponse(CreditScore(750, "Good", listOf("stable income")))

            val application = LoanApplication("Sumit Vairagar", 500000, "home")
            agent.evaluateCredit(application, context)

            val prompt = context.llmInvocations.first().prompt
            assertTrue(prompt.contains("500000")) {
                "Expected prompt to contain loan amount but was: $prompt"
            }
        }
    }

    @Nested
    inner class `assessRisk prompt` {

        @Test
        fun `must contain credit score`() {
            val context = FakeOperationContext()
            context.expectResponse(RiskAssessment(true, "Low", "Good credit history"))

            val application = LoanApplication("Sumit Vairagar", 500000, "home")
            val score = CreditScore(750, "Good", listOf("stable income", "low debt"))

            agent.assessRisk(application, score, context)

            val prompt = context.llmInvocations.first().prompt
            assertTrue(prompt.contains("750")) {
                "Expected prompt to contain credit score but was: $prompt"
            }
        }
    }

    @Nested
    inner class `generateDecision prompt` {

        @Test
        fun `must contain risk level`() {
            val context = FakeOperationContext()
            context.expectResponse(
                LoanDecision(true, "Congratulations!", listOf("Submit documents", "Visit branch"))
            )

            val application = LoanApplication("Sumit Vairagar", 500000, "home")
            val assessment = RiskAssessment(true, "Low", "Good credit history")

            agent.generateDecision(application, assessment, context)

            val prompt = context.llmInvocations.first().prompt
            assertTrue(prompt.contains("Low")) {
                "Expected prompt to contain risk level but was: $prompt"
            }
        }

        @Test
        fun `must not use tool groups — this is a pure LLM transform`() {
            val context = FakeOperationContext()
            context.expectResponse(
                LoanDecision(true, "Congratulations!", listOf("Submit documents"))
            )

            val application = LoanApplication("Sumit Vairagar", 500000, "home")
            val assessment = RiskAssessment(true, "Low", "Good credit history")

            agent.generateDecision(application, assessment, context)

            val toolGroups = context.llmInvocations.first().interaction.toolGroups
            assertTrue(toolGroups.isEmpty()) {
                "generateDecision should not use any tool groups"
            }
        }
    }
}
