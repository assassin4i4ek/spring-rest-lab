package ua.kpi.its.lab.rest.svc.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.entity.Vehicle
import ua.kpi.its.lab.rest.repo.VehicleRepository
import ua.kpi.its.lab.rest.svc.VehicleService

@Service
class VehicleServiceImpl @Autowired constructor(
    private val repository: VehicleRepository
): VehicleService {
    // Your code here
    override fun create(vehicle: Vehicle): Vehicle {
        if (repository.existsById(vehicle.id)) {
            throw IllegalArgumentException("Vehicle with ID = ${vehicle.id} already exists")
        }
        return repository.save(vehicle)
    }

    override fun read(): List<Vehicle> {
        return repository.findAll()
    }

    override fun readByIndex(index: Int): Vehicle {
        return this.read()[index]
    }

    override fun update(vehicle: Vehicle): Vehicle {
        if (!repository.existsById(vehicle.id)) {
            throw IllegalArgumentException("Vehicle with ID = ${vehicle.id} not found")
        }
        return repository.save(vehicle)
    }

    override fun delete(vehicle: Vehicle) {
        if (!repository.existsById(vehicle.id)) {
            throw IllegalArgumentException("Vehicle with ID = ${vehicle.id} not found")
        }
        repository.deleteById(vehicle.id)
    }

    override fun deleteByIndex(index: Int): Vehicle {
        val target = this.readByIndex(index)
        this.delete(target)
        return target
    }
}