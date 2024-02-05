package org.egi.virtualdoctorserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VirtualDoctorServerApplication

fun main(args: Array<String>) {
	runApplication<VirtualDoctorServerApplication>(*args)
}
