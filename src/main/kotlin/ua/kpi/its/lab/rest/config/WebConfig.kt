package ua.kpi.its.lab.rest.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router
import ua.kpi.its.lab.rest.dto.VehicleRequest
import ua.kpi.its.lab.rest.svc.VehicleService
import java.text.SimpleDateFormat

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val builder = Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .dateFormat(SimpleDateFormat("yyyy-MM-dd"))
            .modulesToInstall(KotlinModule.Builder().build())

        converters
            .add(MappingJackson2HttpMessageConverter(builder.build()))
    }

    @Bean
    fun functionalRoutes(vehicleService: VehicleService): RouterFunction<*> = router {
        fun wrapNotFoundError(call: () -> Any): ServerResponse {
            return try {
                val result = call()
                ok().body(result)
            }
            catch (e: IllegalArgumentException) {
                notFound().build()
            }
        }

        "/fn".nest {
            "/vehicles".nest {
                GET("") {
                    ok().body(vehicleService.read())
                }
                GET("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { vehicleService.readById(id) }
                }
                POST("") { req ->
                    val vehicle = req.body<VehicleRequest>()
                    ok().body(vehicleService.create(vehicle))
                }
                PUT("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    val vehicle = req.body<VehicleRequest>()
                    wrapNotFoundError { vehicleService.updateById(id, vehicle) }
                }
                DELETE("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    wrapNotFoundError { vehicleService.deleteById(id)}
                }
            }

        }
    }
}