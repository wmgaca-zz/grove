package grove.persistence

interface Storage {
    fun createCycle(name: String, financialYear: Int, quarter: Int)
    fun cycles() : List<Cycle>
}