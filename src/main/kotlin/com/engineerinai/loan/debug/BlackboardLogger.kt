package com.engineerinai.loan.debug

import com.embabel.agent.event.ActionExecutionStartEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * EP05: Blackboard Logger
 *
 * Listens for action start events and prints the current
 * blackboard contents before each action runs.
 *
 * AgentProcess extends Blackboard directly — so we can call
 * .objects on it without going through a separate blackboard field.
 *
 * Remove this in production. It's a learning tool.
 */
@Component
class BlackboardLogger {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun onActionStart(event: ActionExecutionStartEvent) {
        val process = event.agentProcess
        logger.info("=== Blackboard before [{}] ===", event.action.name)
        process.objects.forEachIndexed { index, obj ->
            logger.info("  [{}] {}: {}", index, obj::class.simpleName, obj)
        }
        logger.info("================================")
    }
}
