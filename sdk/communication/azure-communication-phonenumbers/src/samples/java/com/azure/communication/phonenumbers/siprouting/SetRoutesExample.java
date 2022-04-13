// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.TrunkRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Update SIP routing configuration example.
 */
public class SetRoutesExample {
    private static final String CONNECTION_STRING = "endpoint=https://test-jannovak.communication.azure.com/;accesskey=A58V9gbYUus4cx1SvEF1QprJCqo9a2uy6aRtkpZ+2Zy4Khh/RNFZMoFMMZ1IFTwDa2Zqb3m9fbDPCe295kDXHw==";

//    private static final String CONNECTION_STRING
//        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final String FIRST_ROUTE_NAME = "first route name";
    private static final String SECOND_ROUTE_NAME = "second route name";
    private static final String FIRST_ROUTE_PATTERN = ".*";
    private static final String SECOND_ROUTE_PATTERN = ".*5";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SipRoutingClient client = new SipRoutingClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        List<TrunkRoute> routes = client.setRoutes(prepareRoutes());
        print(routes);
    }

    private static List<TrunkRoute> prepareRoutes() {
        return asList(
            new TrunkRoute().setName(FIRST_ROUTE_NAME).setNumberPattern(FIRST_ROUTE_PATTERN),
            new TrunkRoute().setName(SECOND_ROUTE_NAME).setNumberPattern(SECOND_ROUTE_PATTERN)
        );
    }

    private static void print(List<TrunkRoute> routes) {
        try {
            System.out.printf("SIP Trunk Routes: %s%n",
                MAPPER.writeValueAsString(routes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
