package org.egi.virtualdoctorserver.filters

import kotlin.jvm.Throws

interface Filter<T> {
    /**
     * Filters a list of items based on the filters provided. The filters are a map of filter names to filter values.
     * If the filter name is not recognized, it throws an exception.
     * To see the available filters, check the filterTypes property of the specific subclass you are using.
     * @param items the list of items to filter
     * @param filters a map of filter names to filter values
     */
    @Throws(NoSuchElementException::class)
    fun filter(items: List<T>, filters: Map<String, String>): List<T>
}

/**
 * A generic filter that can be used to filter any type of object.
 * Create subclass of GenericFilter and inside it create a sealed class that extends Type.
 * Under Type, create objects for all the filters that can be applied to the object and implement the filter
 * function for each of them.
 * @param T the type of object to filter
 * @property filterTypes the types of filters that can be applied to the object, has to be overridden by the subclass
 * @see Type
 */
abstract class GenericFilter<T> : Filter<T> {
    abstract val filterTypes: List<Type<T>>
    override fun filter(items: List<T>, filters: Map<String, String>): List<T> {
        var items = items
        val filterTypes = filters.mapKeys { Type.mapStringToFilterType(it.key, filterTypes) }
        filterTypes.forEach { (filterType, value) -> items = filterType.filter(items, value)}
        return items
    }

    /**
     * A type of filter that can be applied to an object.
     * @param filterName the name of the filter
     * @see filter
     * @see mapStringToFilterType
     */
    sealed class Type<T>(val filterName: String){
        /**
         * Filters a list of items based on the filter value. Must be overridden by the subclass.
         */
        abstract fun filter(items: List<T>, filterValue: String): List<T>

        companion object {
            fun <T> mapStringToFilterType(filterName: String, filterTypes: List<Type<T>>): Type<T> {
                return filterTypes.first { it.filterName == filterName }
            }
        }
    }

}
