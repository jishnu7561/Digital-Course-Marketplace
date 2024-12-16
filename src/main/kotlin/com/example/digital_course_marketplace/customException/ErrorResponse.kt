package com.example.digital_course_marketplace.customException

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse {

    var timeStamp: LocalDateTime? = null
    var status: String = ""
    var code =0
    var message: String? =" "

    constructor() {
        timeStamp = LocalDateTime.now()
    }

    constructor(httpStatus: HttpStatus, message:String?) {
        timeStamp = LocalDateTime.now()
        code = httpStatus.value()
        status = httpStatus.name
        this.message = message
    }


}
