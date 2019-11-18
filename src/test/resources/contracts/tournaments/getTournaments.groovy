package contracts.tournaments

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description("should return all Tournaments")
    request {
        method("GET")
        url("/tournaments")
    }
    response {
        status(HttpStatus.OK.value())
        headers {
            contentType("application/hal+json")
        }
        body(
            [
                "_embedded": [
                    "tournaments": [
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
                        ],
                        [
                            "id"    : "9",
                            "name"  : "Tournament-9",
                            "description"  : "Description for Tournament-9",
                            "organizerId"  : "10",
                            "startDate"  : "2020-02-22",
                            "_links": [
                                "self": [
                                    "href": "http://localhost/tournaments/9"
                                ]
                            ]
                        ]
                    ],
                ],
                "_links"   : [
                    "self": [
                        "href": "http://localhost/tournaments"
                    ]
                ]
            ]
        )
    }
}