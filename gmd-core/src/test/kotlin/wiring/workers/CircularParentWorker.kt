package wiring.workers

import wiring.workers.types.NopWorker

class CircularParentWorker : NopWorker() {
    init {
        mustRunAfter(
            CircularChildWorker::class
        )
    }
}