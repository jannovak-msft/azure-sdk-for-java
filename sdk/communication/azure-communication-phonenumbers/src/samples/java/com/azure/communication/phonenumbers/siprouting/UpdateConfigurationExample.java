// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.implementation.models.SipConfiguration;
import com.azure.communication.phonenumbers.siprouting.models.Trunk;
import com.azure.communication.phonenumbers.siprouting.models.TrunkRoute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Update SIP routing configuration example.
 */
public class UpdateConfigurationExample {
    private static final String CONNECTION_STRING
        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final String TRUNK_FQDN = "trunk.mysite.com";
    private static final int TRUNK_SIP_SIGNALING_PORT = 1234;
    private static final String ROUTE_NAME = "route name";
    private static final String ROUTE_PATTERN = ".*";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SipRoutingClient client = new SipRoutingClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        SipConfiguration configuration = client.setSipConfiguration(prepareConfiguration());
        printConfiguration(configuration);
    }

    private static SipConfiguration prepareConfiguration() {
        HashMap<String, Trunk> trunks = new HashMap<>();
        trunks.put(TRUNK_FQDN, new Trunk().setSipSignalingPort(TRUNK_SIP_SIGNALING_PORT));
        List<TrunkRoute> routes =
            asList(new TrunkRoute().setName(ROUTE_NAME).setNumberPattern(ROUTE_PATTERN).setTrunks(asList(TRUNK_FQDN)));
        return new SipConfiguration().setTrunks(trunks).setRoutes(routes);
    }

    private static void printConfiguration(SipConfiguration sipConfiguration) {
        try {
            System.out.printf("SIP routing configuration:%n%s%n", MAPPER.writeValueAsString(sipConfiguration));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
