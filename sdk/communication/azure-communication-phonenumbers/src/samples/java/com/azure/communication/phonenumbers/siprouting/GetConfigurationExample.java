// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.SipConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Get SIP routing configuration example.
 */
public class GetConfigurationExample {
    private static final String CONNECTION_STRING
        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SipRoutingClient client = new SipRoutingClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        SipConfiguration configuration = client.getSipConfiguration();
        printConfiguration(configuration);
    }

    private static void printConfiguration(SipConfiguration configuration) {
        try {
            System.out.printf("SIP routing configuration:%n%s%n", MAPPER.writeValueAsString(configuration));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
