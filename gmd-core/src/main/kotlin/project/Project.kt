package project

interface Project<T : Project<T>> {
    fun initialize(): T
    fun build(): T
}