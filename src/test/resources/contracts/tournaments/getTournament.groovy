package contracts.tournaments

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("should return the Tournament with the given ID")
    request {
        method("GET")
        url("/tournaments/5")
    }
    response {
        status(HttpStatus.OK.value())
        headers {
            contentType("application/hal+json")
        }
        body(
            [
                "id"    : "5",
                "name"  : "Tournament-5",
                "description"  : "Description for Tournament-5",
                "organizerId"  : "10",
                "startDate"  : "2020-02-22",
                "_links": [
                    "self": [
                        "href": "http://localhost/tournaments/5"
                    ]
                ]
            ]
        )
    }
}