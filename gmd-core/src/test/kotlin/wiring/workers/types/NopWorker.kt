package wiring.workers.types

import build.workers.types.Worker
import elements.types.Element

open class NopWorker : Worker() {
    override fun processElement(element: Element): Element = element

    override fun toString(): String {
        return this::class.simpleName!!
    }
}