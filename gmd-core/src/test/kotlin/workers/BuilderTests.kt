package workers

import build.Builder
import build.workers.types.WorkerCircularDependencyException
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.Test
import wiring.workers.*

class BuilderTests {
    @Test
    fun `If all workers depend on each other in a single chain, they are added in the correct order`() {
        val first = Worker1()
        val second = Worker2().apply { mustRunAfter(Worker1::class) }
        val third = Worker3().apply { mustRunAfter(Worker2::class) }

        Builder(first, second, third).workers.apply {
            size `should be equal to` 3
            get(0) `should be` first
            get(1) `should be` second
            get(2) `should be` third
        }
    }

    @Test
    fun `If two workers depend on same one, they are both added to the list after`() {
        val dep = Worker9()
        val first = Worker1().apply { mustRunAfter(dep::class) }
        val second = Worker2().apply { mustRunAfter(dep::class) }

        Builder(second, dep, first).workers.apply {
            size `should be equal to` 3
            first() `should be equal to` dep
        }
    }

    @Test
    fun `Circular worker dependency throws appropriate exception`() {
        val first = Worker1().apply { mustRunAfter(Worker2::class) }
        val second = Worker2().apply { mustRunAfter(Worker1::class) }
        invoking {
            Builder(first, second)
        } `should throw` WorkerCircularDependencyException::class
    }
}