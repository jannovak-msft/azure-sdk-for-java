// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.Trunk;
import com.azure.communication.phonenumbers.siprouting.models.TrunkRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * SIP Routing Example.
 */
public class Example {
    private static final String CONNECTION_STRING
        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        // Build client
        SipRoutingClient client = new SipRoutingClientBuilder()
            .connectionString(CONNECTION_STRING)
            .buildClient();

        // Set trunks
        client.setTrunks(asList(
            new Trunk().setFqdn("sbc.one.domain.com").setSipSignalingPort(5678),
            new Trunk().setFqdn("sbc.two.domain.com").setSipSignalingPort(5678)
        ));

        // Set routes
        client.setRoutes(asList(
            new TrunkRoute()
                .setName("first route name")
                .setNumberPattern("[1-9][0-9]{3,23}")
                .setTrunks(asList("sbc.one.domain.com")),
            new TrunkRoute()
                .setName("second route name")
                .setNumberPattern(".*")
                .setTrunks(asList("sbc.two.domain.com", "sbc.one.domain.com"))
        ));

        // Update a trunk
        client.setTrunk(new Trunk().setFqdn("sbc.one.domain.com").setSipSignalingPort(1234));

        // Update a route
        client.setRoute(new TrunkRoute()
            .setName("second route name")
            .setNumberPattern("123.*")
            .setTrunks(asList("sbc.two.domain.com"))
        );

        // Get
        List<Trunk> trunks = client.getTrunks();
        List<TrunkRoute> routes = client.getRoutes();
        print(trunks, routes);
    }

    private static void print(List<Trunk> trunks, List<TrunkRoute> routes) {
        try {
            System.out.printf("SIP Trunks: %s%nSIP Trunk Routes: %s%n",
                MAPPER.writeValueAsString(trunks),
                MAPPER.writeValueAsString(routes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
