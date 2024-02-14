package build

import build.workers.types.Worker
import build.workers.types.WorkerCircularDependencyException
import build.workers.types.WorkerClass
import elements.Document
import kotlin.reflect.full.createInstance

/**
 * Manages MD -> GMD build process for a single project.
 * @param workers Set of [Worker] instances to run during the build.
 */
open class Builder(workers: Set<Worker>) {
    /**
     * @param workers Set of [Worker] instances to run during the build.
     */
    constructor(vararg workers: Worker) : this(workers.toSet())

    /**
     * [Worker] instances to run during the build, ordered based on their [Worker.runAfter] definitions.
     */
    val workers: List<Worker> = arrangeWorkers(workers)

    /**
     * Orders a set of [Worker] instances based on their [Worker.runAfter] definitions.
     * @throws WorkerCircularDependencyException If two workers depend on each other.
     */
    protected fun arrangeWorkers(workers: Set<Worker>): List<Worker> {
        val list = mutableListOf<Worker>()
        val instances = workers.associateBy { it::class }.toMutableMap()

        fun putOnList(worker: Worker, parents: Set<Worker> = emptySet()) {
            if (parents.contains(worker)) {
                val parent = parents.first { it.runAfter.contains(worker::class) }
                throw WorkerCircularDependencyException("Circular dependency between $worker and $parent.")
            }

            worker.runAfter.forEach {
                putOnList(
                    worker = instances.getOrPut(it) { it.createInstance() },
                    parents = parents.plus(worker)
                )
            }
            if (!list.contains(worker))
                list.add(worker)
        }

        instances.values.forEach(::putOnList)

        return list
    }

    /**
     * Run configured [Worker]s on the [Document]s to modify them into full-featured GMD trees.
     */
    fun build(documents: Iterable<Document>): Iterable<Document> =
        documents.map { document ->
            workers.fold(document) { doc, worker ->
                worker.processTree(doc) as Document
            }
        }

    fun build(document: Document): Document =
        build(listOf(document)).first()

    companion object {
        /**
         * Returns a new GMD [Builder] with automatically created [Worker] instances.
         * @param workers Classes of workers to instantiate and add to the build.
         */
        fun create(workers: Set<WorkerClass>) = Builder(
            workers.map { it.createInstance() }.toSet()
        )

        /**
         * See [create].
         */
        fun create(vararg workers: WorkerClass) = create(workers.toSet())
    }
}