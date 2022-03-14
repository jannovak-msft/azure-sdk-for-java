// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.Trunk;
import com.azure.communication.phonenumbers.siprouting.models.TrunkRoute;
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
     * Sample code for getting SIP trunks.
     *
     * @return the SIP trunks.
     */
    public List<Trunk> getTrunks() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-getTrunks
        List<Trunk> trunks = sipRoutingClient.getTrunks();
        for (Trunk trunk : trunks) {
            System.out.println("Trunk FQDN: " + trunk.getFqdn());
            System.out.println("Trunk SIP signaling port: " + trunk.getSipSignalingPort());
        }
        // END: readme-sample-getTrunks

        return trunks;
    }

    /**
     * Sample code for getting SIP trunk routes.
     *
     * @return the SIP trunk routes.
     */
    public List<TrunkRoute> getRoutes() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-getRoutes
        List<TrunkRoute> routes = sipRoutingClient.getRoutes();
        for (TrunkRoute route : routes) {
            System.out.println("Route name: " + route.getName());
            System.out.println("Route description: " + route.getDescription());
            System.out.println("Route number pattern: " + route.getNumberPattern());
            System.out.println("Route trunks: " + String.join(",", route.getTrunks()));
        }
        // END: readme-sample-getRoutes

        return routes;
    }

    /**
     * Sample code for setting SIP trunks.
     *
     * @return the SIP trunks.
     */
    public List<Trunk> setTrunks() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-setTrunks
        List<Trunk> trunks = sipRoutingClient.setTrunks(asList(
            new Trunk().setFqdn("trunk1.mysite.com").setSipSignalingPort(12345),
            new Trunk().setFqdn("trunk2.mysite.com").setSipSignalingPort(23456)
        ));
        for (Trunk trunk : trunks) {
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
    public List<TrunkRoute> setRoutes() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-setRoutes
        List<TrunkRoute> routes = sipRoutingClient.setRoutes(asList(
            new TrunkRoute().setName("route name1").setNumberPattern(".*")
                .setTrunks(asList("trunk1.mysite.com", "trunk2.mysite.com")),
            new TrunkRoute().setName("route name2").setNumberPattern(".*9")
                .setTrunks(asList("trunk2.mysite.com"))
        ));
        for (TrunkRoute route : routes) {
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
    public Trunk deleteTrunk() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-deleteTrunk
        Trunk trunk = sipRoutingClient.deleteTrunk("trunk1.mysite.com");
        System.out.println("Deleted trunk FQDN: " + trunk.getFqdn());
        System.out.println("Deleted trunk SIP signaling port: " + trunk.getSipSignalingPort());
        // END: readme-sample-deleteTrunk

        return trunk;
    }

    /**
     * Sample code for deleting a SIP trunk route.
     *
     * @return the deleted SIP trunk route.
     */
    public TrunkRoute deleteRoute() {
        SipRoutingClient sipRoutingClient = createSipRoutingClient();

        // BEGIN: readme-sample-deleteRoute
        TrunkRoute route = sipRoutingClient.deleteRoute("route name1");
        System.out.println("Deleted route name: " + route.getName());
        System.out.println("Deleted route description: " + route.getDescription());
        System.out.println("Deleted route number pattern: " + route.getNumberPattern());
        System.out.println("Deleted route trunks: " + String.join(",", route.getTrunks()));
        // END: readme-sample-deleteRoute

        return route;
    }
}
