// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.Trunk;
import com.azure.communication.phonenumbers.siprouting.models.TrunkRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Get SIP routing configuration example.
 */
public class GetExample {
    private static final String CONNECTION_STRING
        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SipRoutingClient client = new SipRoutingClientBuilder().connectionString(CONNECTION_STRING).buildClient();
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
