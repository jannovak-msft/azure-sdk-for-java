// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.SipTrunk;
import com.azure.communication.phonenumbers.siprouting.models.SipTrunkRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Update SIP routing configuration example.
 */
public class SetExample {
    private static final String CONNECTION_STRING = "endpoint=https://test-jannovak.communication.azure.com/;accesskey=A58V9gbYUus4cx1SvEF1QprJCqo9a2uy6aRtkpZ+2Zy4Khh/RNFZMoFMMZ1IFTwDa2Zqb3m9fbDPCe295kDXHw==";

//    private static final String CONNECTION_STRING
//        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final String TRUNK_FQDN = "trunk.mysite.com";
    private static final int TRUNK_SIP_SIGNALING_PORT = 1234;
    private static final String ROUTE_NAME = "route name";
    private static final String ROUTE_PATTERN = ".*";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SipRoutingClient client = new SipRoutingClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        List<SipTrunk> trunks = client.setTrunks(prepareTrunks());
        List<SipTrunkRoute> routes = client.setRoutes(prepareRoutes());
        print(trunks, routes);
    }

    private static List<SipTrunk> prepareTrunks() {
        return asList(new SipTrunk(TRUNK_FQDN, TRUNK_SIP_SIGNALING_PORT));
    }

    private static List<SipTrunkRoute> prepareRoutes() {
        return asList(
            new SipTrunkRoute().setName(ROUTE_NAME).setNumberPattern(ROUTE_PATTERN).setTrunks(asList(TRUNK_FQDN)));
    }

    private static void print(List<SipTrunk> trunks, List<SipTrunkRoute> routes) {
        try {
            System.out.printf("SIP Trunks: %s%nSIP Trunk Routes: %s%n",
                MAPPER.writeValueAsString(trunks),
                MAPPER.writeValueAsString(routes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
