package contracts.tournaments

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("should create a Tournament from the input form")
    request {
        method("POST")
        url("/tournaments")
        headers {
            contentType("application/hal+json")
        }
        body(
            [
                "name"  : "Tournament-5",
                "description"  : "Description for Tournament-5",
                "organizerId"  : "10",
                "startDate"  : "2020-02-22"
            ]
        )
    }
    response {
        status(HttpStatus.CREATED.value())
        headers {
            location().contains("http://localhost/tournaments/5")
        }
    }
}