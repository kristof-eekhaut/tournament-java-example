package contracts.tournaments

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("should change the name and description of a Tournament")
    request {
        method("PATCH")
        url("/tournaments/5/change-information")
        headers {
            contentType("application/hal+json")
        }
        body(
            [
                "name"  : "Updated Tournament",
                "description"  : "Updated description for Tournament"
            ]
        )
    }
    response {
        status(HttpStatus.OK.value())
    }
}