package org.egi.virtualdoctorserver.exceptions

class NoPermissionException(
    message: String = "You do not have permission to perform this action"): Exception(message)