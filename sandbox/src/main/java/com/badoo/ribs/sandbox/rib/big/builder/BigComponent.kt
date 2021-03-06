package com.badoo.ribs.sandbox.rib.big.builder

import com.badoo.ribs.core.modality.BuildParams
import com.badoo.ribs.sandbox.rib.big.Big
import com.badoo.ribs.sandbox.rib.big.BigNode
import com.badoo.ribs.sandbox.rib.small.Small
import dagger.BindsInstance

@BigScope
@dagger.Component(
    modules = [BigModule::class],
    dependencies = [Big.Dependency::class]
)
internal interface BigComponent : Small.Dependency {

    @dagger.Component.Factory
    interface Factory {
        fun create(
            dependency: Big.Dependency,
            @BindsInstance customisation: Big.Customisation,
            @BindsInstance buildParams: BuildParams<Nothing?>
        ): BigComponent
    }

    fun node(): BigNode
}
