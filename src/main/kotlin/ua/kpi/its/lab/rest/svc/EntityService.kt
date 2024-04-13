package ua.kpi.its.lab.rest.svc

import ua.kpi.its.lab.rest.entity.Vehicle

interface VehicleService {
    /**
     * Creates a new Vehicle record.
     *
     * @param vehicle: The Vehicle instance to be inserted
     * @return: The recently created Vehicle instance
     */
    fun create(vehicle: Vehicle): Vehicle
    /**
     * Reads all created Vehicle records.
     *
     * @return: List of created Vehicle records
     */
    fun read(): List<Vehicle>
    /**
     * Reads a Vehicle record by its index.
     * The order is determined by the order of creation.
     *
     * @param index: The index of Vehicle record
     * @return: The Vehicle instance at index
     */
    fun readByIndex(index: Int): Vehicle
    /**
     * Updates a Vehicle record data.
     *
     * @param vehicle: The Vehicle instance to be updated (valid id is required)
     * @return: The updated Vehicle record
     */
    fun update(vehicle: Vehicle): Vehicle
    /**
     * Deletes a Vehicle record data.
     *
     * @param vehicle: The Vehicle instance to be deleted (valid `id` is required)
     */
    fun delete(vehicle: Vehicle)
    /**
     * Deletes a Vehicle record by its index.
     * The order is determined by the order of creation.
     *
     * @param index: The index of Vehicle record to delete
     * @return: The deleted Vehicle instance at index
     */
    fun deleteByIndex(index: Int): Vehicle
}