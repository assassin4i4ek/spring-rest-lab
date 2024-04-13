package ua.kpi.its.lab.rest.repo

import org.springframework.data.jpa.repository.JpaRepository
import ua.kpi.its.lab.rest.entity.Battery
import ua.kpi.its.lab.rest.entity.Vehicle

interface VehicleRepository : JpaRepository<Vehicle, Long> {

}

interface BatteryRepository : JpaRepository<Battery, Long> {

}