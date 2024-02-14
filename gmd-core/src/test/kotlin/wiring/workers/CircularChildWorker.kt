package wiring.workers

import wiring.workers.types.NopWorker

class CircularChildWorker : NopWorker() {
    init {
        mustRunAfter(
            CircularParentWorker::class
        )
    }
}