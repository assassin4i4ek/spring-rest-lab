package ua.kpi.its.lab.rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.kpi.its.lab.rest.dto.VehicleRequest
import ua.kpi.its.lab.rest.dto.VehicleResponse
import ua.kpi.its.lab.rest.svc.VehicleService

@RestController
class VehicleController @Autowired constructor(
    private val vehicleService: VehicleService
) {
    /**
     * Gets the list of all vehicles
     *
     * @return: List of VehicleResponse
     */
    @GetMapping(path = ["", "/"])
    fun vehicles(): List<VehicleResponse> = vehicleService.read()

    /**
     * Reads the vehicle by its id
     *
     * @param id: id of the vehicle
     * @return: VehicleResponse for the given id
     */
    @GetMapping("{id}")
    fun readVehicle(@PathVariable("id") id: Long): ResponseEntity<VehicleResponse> {
        return wrapNotFound { vehicleService.readById(id) }
    }

    /**
     * Creates a new vehicle instance
     *
     * @param vehicle: VehicleRequest with set properties
     * @return: VehicleResponse for the created vehicle
     */
    @PostMapping(path = ["", "/"])
    fun createVehicle(@RequestBody vehicle: VehicleRequest): VehicleResponse {
        return vehicleService.create(vehicle)
    }

    /**
     * Updates existing vehicle instance
     *
     * @param vehicle: VehicleRequest with properties set
     * @return: VehicleResponse of the updated vehicle
     */
    @PutMapping("{id}")
    fun updateVehicle(
        @PathVariable("id") id: Long,
        @RequestBody vehicle: VehicleRequest
    ): ResponseEntity<VehicleResponse> {
        return wrapNotFound { vehicleService.updateById(id, vehicle)}
    }

    /**
     * Deletes existing vehicle instance
     *
     * @param id: id of the vehicle
     * @return: VehicleResponse of the deleted vehicle
     */
    @DeleteMapping("{id}")
    fun deleteVehicle(
        @PathVariable("id") id: Long
    ): ResponseEntity<VehicleResponse> {
        return wrapNotFound { vehicleService.deleteById(id) }
    }

    fun <T>wrapNotFound(call: () -> T): ResponseEntity<T> {
        return try {
            // call function for result
            val result = call()
            // return "ok" response with result body
            ResponseEntity.ok(result)
        }
        catch (e: IllegalArgumentException) {
            // catch not-found exception
            // return "404 not-found" response
            ResponseEntity.notFound().build()
        }
    }
}