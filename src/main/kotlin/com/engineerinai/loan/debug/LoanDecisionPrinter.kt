package com.engineerinai.loan.debug

import com.embabel.agent.event.AgentProcessCompletedEvent
import com.engineerinai.loan.domain.LoanDecision
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * Prints a readable summary when the agent completes.
 * Pulls the final LoanDecision off the blackboard and displays it nicely.
 */
@Component
class LoanDecisionPrinter {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun onAgentCompleted(event: AgentProcessCompletedEvent) {
        val decision = event.agentProcess.objects
            .filterIsInstance<LoanDecision>()
            .lastOrNull() ?: return

        logger.info("")
        logger.info("╔══════════════════════════════════════╗")
        logger.info("║         LOAN DECISION RESULT         ║")
        logger.info("╠══════════════════════════════════════╣")
        logger.info("║ Decision : {}",
            if (decision.approved) "✅ APPROVED" else "❌ REJECTED")
        logger.info("║ Message  : {}", decision.message)
        logger.info("║ Next steps:")
        decision.nextSteps.forEach { step ->
            logger.info("║   → {}", step)
        }
        logger.info("╚══════════════════════════════════════╝")
        logger.info("")
    }
}
