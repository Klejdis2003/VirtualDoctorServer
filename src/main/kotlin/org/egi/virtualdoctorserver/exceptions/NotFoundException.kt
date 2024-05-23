package org.egi.virtualdoctorserver.exceptions

class NotFoundException(
    message: String = "The required entity does not exist in the database."): Exception(message)