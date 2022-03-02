// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.TrunkRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Update SIP routing configuration example.
 */
public class DeleteRouteExample {
    private static final String CONNECTION_STRING = "endpoint=https://test-jannovak.communication.azure.com/;accesskey=A58V9gbYUus4cx1SvEF1QprJCqo9a2uy6aRtkpZ+2Zy4Khh/RNFZMoFMMZ1IFTwDa2Zqb3m9fbDPCe295kDXHw==";

//    private static final String CONNECTION_STRING
//        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final String ROUTE_NAME = "route name";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SipRoutingClient client = new SipRoutingClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        print(client.getRoutes());
        TrunkRoute route = client.deleteRoute(ROUTE_NAME);
        print(client.getRoutes());
    }

    private static void print(List<TrunkRoute> routes) {
        try {
            System.out.printf("SIP Trunk Routes: %s%n", MAPPER.writeValueAsString(routes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
