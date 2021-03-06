package com.badoo.ribs.sandbox.rib.hello_world

import android.os.Parcelable
import com.badoo.ribs.core.modality.BuildParams
import com.badoo.ribs.routing.Routing
import com.badoo.ribs.routing.action.AttachRibRoutingAction.Companion.attach
import com.badoo.ribs.routing.action.RoutingAction
import com.badoo.ribs.routing.action.RoutingAction.Companion.noop
import com.badoo.ribs.routing.router.Router
import com.badoo.ribs.routing.source.RoutingSource
import com.badoo.ribs.routing.source.RoutingSource.Companion.permanent
import com.badoo.ribs.sandbox.rib.hello_world.HelloWorldRouter.Configuration
import com.badoo.ribs.sandbox.rib.hello_world.HelloWorldRouter.Configuration.Content
import com.badoo.ribs.sandbox.rib.hello_world.HelloWorldRouter.Configuration.Permanent.Small
import com.badoo.ribs.sandbox.rib.small.builder.SmallBuilder
import kotlinx.android.parcel.Parcelize

class HelloWorldRouter(
    buildParams: BuildParams<Nothing?>,
    routingSource: RoutingSource<Configuration>,
    private val smallBuilder: SmallBuilder
): Router<Configuration>(
    buildParams = buildParams,
    routingSource = routingSource + permanent(Small)
) {
    sealed class Configuration : Parcelable {
        sealed class Permanent : Configuration() {
            @Parcelize object Small : Permanent()
        }
        sealed class Content : Configuration() {
            @Parcelize object Default : Content()
        }
    }

    override fun resolve(routing: Routing<Configuration>): RoutingAction =
        when (routing.configuration) {
            Small -> attach { smallBuilder.build(it) }
            Content.Default -> noop()
    }
}
