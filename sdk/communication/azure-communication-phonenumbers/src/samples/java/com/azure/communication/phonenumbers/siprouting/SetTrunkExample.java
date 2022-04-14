// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.SipTrunk;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Update SIP routing configuration example.
 */
public class SetTrunkExample {
    private static final String CONNECTION_STRING = "endpoint=https://test-jannovak.communication.azure.com/;accesskey=A58V9gbYUus4cx1SvEF1QprJCqo9a2uy6aRtkpZ+2Zy4Khh/RNFZMoFMMZ1IFTwDa2Zqb3m9fbDPCe295kDXHw==";

//    private static final String CONNECTION_STRING
//        = "endpoint=https://RESOURCE_NAME.communication.azure.com/;accesskey=SECRET";

    private static final String TRUNK_FQDN = "trunk1.mysite.com";
    private static final int TRUNK_SIP_SIGNALING_PORT = 12345;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SipRoutingClient client = new SipRoutingClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        client.setTrunk(prepareTrunk());
        print(client.listTrunks());
    }

    private static SipTrunk prepareTrunk() {
        return new SipTrunk(TRUNK_FQDN, TRUNK_SIP_SIGNALING_PORT);
    }

    private static void print(List<SipTrunk> trunks) {
        try {
            System.out.printf("SIP Trunks: %s%n", MAPPER.writeValueAsString(trunks));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
