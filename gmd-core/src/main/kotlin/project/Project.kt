package project

interface Project<T : Project<T>> {
    val pages: List<Page>
    fun initialize(): T
    fun build(): T
}