package com.badoo.ribs.core.routing.configuration.action.single

import android.os.Parcelable
import com.badoo.ribs.core.Node
import com.badoo.ribs.core.routing.activator.RoutingActivator
import com.badoo.ribs.core.routing.configuration.ConfigurationContext.Resolved
import com.badoo.ribs.core.routing.configuration.action.ActionExecutionParams
import com.badoo.ribs.core.routing.configuration.feature.ConfigurationFeature.Effect
import com.badoo.ribs.core.routing.configuration.feature.EffectEmitter
import com.badoo.ribs.core.routing.history.Routing
import com.badoo.ribs.core.routing.transition.TransitionElement

/**
 * Attaches [Node]s to a parentNode without their views
 */
internal class AddAction<C : Parcelable>(
    private val emitter: EffectEmitter<C>,
    private val routing: Routing<C>,
    private var item: Resolved<C>,
    private val activator: RoutingActivator<C>
) : Action<C> {

    object Factory: ActionFactory {
        override fun <C : Parcelable> create(
            params: ActionExecutionParams<C>
        ): Action<C> = AddAction(
            emitter = params.transactionExecutionParams.emitter,
            routing = params.routing,
            item = params.item,
            activator = params.transactionExecutionParams.activator
        )
    }

    override var canExecute: Boolean =
        true

    override fun onBeforeTransition() {
        activator.add(routing, item.nodes)
        emitter.onNext(
            Effect.Individual.Added(routing, item)
        )
    }

    override fun onTransition(forceExecute: Boolean) {
        emitter.onNext(
            Effect.Individual.PendingRemovalFalse(routing)
        )
    }

    override fun onFinish(forceExecute: Boolean) {
    }

    override val transitionElements: List<TransitionElement<C>> =
        emptyList()
}
