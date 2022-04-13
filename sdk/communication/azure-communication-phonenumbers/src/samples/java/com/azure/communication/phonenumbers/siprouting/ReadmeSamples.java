// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.SipTrunk;
import com.azure.communication.phonenumbers.siprouting.models.SipTrunkRoute;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.HttpClient;
import com.azure.core.http.netty.NettyAsyncHttpClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;

import java.util.List;

import static java.util.Arrays.asList;

public class ReadmeSamples {

    /**
     * Sample code for creating a sync SIP Routing Client.
     *
     * @return the SIP Routing Client.
     */
    public SipRoutingClient createSipRoutingClient() {
        // BEGIN: readme-sample-createSipRoutingClient
        // You can find your endpoint and access token from your resource in the Azure Portal
        String endpoint = "https://<RESOURCE_NAME>.communication.azure.com";
        AzureKeyCredential keyCredential = new AzureKeyCredential("SECRET");

        // Create an HttpClient builder of your choice and customize it
        HttpClient httpClient = new NettyAsyncHttpClientBuilder().build();

        SipRoutingClient sipRoutingClient = new SipRoutingClientBuilder()
            .endpoint(endpoint)
            .credential(keyCredential)
            .httpClient(httpClient)
            .buildClient();
        // END: readme-sample-createSipRoutingClient

        return sipRoutingClient;
    }

    /**
     * Sample code for creating an async SIP Routing Client.
     *
     * @return the SIP Routing Client.
     */
    public SipRoutingAsyncClient createSipRoutingAsyncClient() {
        // BEGIN: readme-sample-createSipRoutingAsyncClient
        // You can find your endpoint and access token from your resource in the Azure Portal
        String endpoint = "https://<RESOURCE_NAME>.communication.azure.com";
        AzureKeyCredential keyCredential = new AzureKeyCredential("SECRET");

        // Create an HttpClient builder of your choice and customize it
        HttpClient httpClient = new NettyAsyncHttpClientBuilder().build();

        SipRoutingAsyncClient sipRoutingClient = new SipRoutingClientBuilder()
            .endpoint(endpoint)
            .credential(keyCredential)
            .httpClient(httpClient)
            .buildAsyncClient();
        // END: readme-sample-createSipRoutingAsyncClient

        return sipRoutingClient;
    }

    /**
     * Sample code for creating a sync SIP Routing Client using AAD authentication.
     *
     * @return the SIP Routing Client.
     */
    public SipRoutingClient createSipRoutingClientWithAAD() {
        // BEGIN: readme-sample-createSipRoutingClientWithAAD
        // You can find your endpoint and access key from your resource in the Azure Portal
        String endpoint = "https://<RESOURCE_NAME>.communication.azure.com";

        // Create an HttpClient builder of your choice and customize it
        HttpClient httpClient = new NettyAsyncHttpClientBuilder().build();

        SipRoutingClient sipRoutingClient = new SipRoutingClientBuilder()
            .endpoint(endpoint)
            .credential(new DefaultAzureCredentialBuilder().build())
            .httpClient(httpClient)
            .buildClient();
        // END: readme-sample-createSipRoutingClientWithAAD

        return sipRoutingClient;
    }

    /**
     * Sample code for listing SIP trunks.
     *
     * @return the SIP trunks.
     */
    public List<SipTrunk> listTrunks() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-listTrunks
        List<SipTrunk> trunks = sipRoutingClient.listTrunks();
        for (SipTrunk trunk : trunks) {
            System.out.println("Trunk FQDN: " + trunk.getFqdn());
            System.out.println("Trunk SIP signaling port: " + trunk.getSipSignalingPort());
        }
        // END: readme-sample-listTrunks

        return trunks;
    }

    /**
     * Sample code for listing SIP trunk routes.
     *
     * @return the SIP trunk routes.
     */
    public List<SipTrunkRoute> listRoutes() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-listRoutes
        List<SipTrunkRoute> routes = sipRoutingClient.listRoutes();
        for (SipTrunkRoute route : routes) {
            System.out.println("Route name: " + route.getName());
            System.out.println("Route description: " + route.getDescription());
            System.out.println("Route number pattern: " + route.getNumberPattern());
            System.out.println("Route trunks: " + String.join(",", route.getTrunks()));
        }
        // END: readme-sample-listRoutes

        return routes;
    }

    /**
     * Sample code for setting SIP trunks.
     *
     * @return the SIP trunks.
     */
    public List<SipTrunk> setTrunks() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-setTrunks
        List<SipTrunk> trunks = sipRoutingClient.setTrunks(asList(
            new SipTrunk("trunk1.mysite.com", 12345),
            new SipTrunk("trunk2.mysite.com", 23456)
        ));
        for (SipTrunk trunk : trunks) {
            System.out.println("Trunk FQDN: " + trunk.getFqdn());
            System.out.println("Trunk SIP signaling port: " + trunk.getSipSignalingPort());
        }
        // END: readme-sample-setTrunks

        return trunks;
    }

    /**
     * Sample code for setting SIP trunk routes.
     *
     * @return the SIP trunk routes.
     */
    public List<SipTrunkRoute> setRoutes() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-setRoutes
        List<SipTrunkRoute> routes = sipRoutingClient.setRoutes(asList(
            new SipTrunkRoute().setName("route name1").setNumberPattern(".*")
                .setTrunks(asList("trunk1.mysite.com", "trunk2.mysite.com")),
            new SipTrunkRoute().setName("route name2").setNumberPattern(".*9")
                .setTrunks(asList("trunk2.mysite.com"))
        ));
        for (SipTrunkRoute route : routes) {
            System.out.println("Route name: " + route.getName());
            System.out.println("Route description: " + route.getDescription());
            System.out.println("Route number pattern: " + route.getNumberPattern());
            System.out.println("Route trunks: " + String.join(",", route.getTrunks()));
        }
        // END: readme-sample-setRoutes

        return routes;
    }

    /**
     * Sample code for deleting a SIP trunk.
     *
     * @return the deleted SIP trunk.
     */
    public SipTrunk deleteTrunk() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-deleteTrunk
        SipTrunk trunk = sipRoutingClient.deleteTrunk("trunk1.mysite.com");
        System.out.println("Deleted trunk FQDN: " + trunk.getFqdn());
        System.out.println("Deleted trunk SIP signaling port: " + trunk.getSipSignalingPort());
        // END: readme-sample-deleteTrunk

        return trunk;
    }
}
