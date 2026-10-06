package com.engineerinai.loan.debug

import com.embabel.agent.api.event.ActionExecutedEvent
import com.embabel.agent.core.Blackboard
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * EP05: Blackboard Logger
 *
 * Listens for action execution events and prints the current
 * blackboard contents after each action runs.
 *
 * This makes the Blackboard visible — you can see exactly what
 * objects exist and in what order after every step.
 *
 * Remove this in production. It's a learning tool.
 */
@Component
class BlackboardLogger {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun onActionExecuted(event: ActionExecutedEvent) {
        val blackboard: Blackboard = event.agentProcess.blackboard
        logger.info("=== Blackboard after [{}] ===", event.action.name)
        blackboard.objects.forEachIndexed { index, obj ->
            logger.info("  [{}] {} = {}", index, obj::class.simpleName, obj)
        }
        logger.info("==============================")
    }
}
