// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.models.SipTrunk;
import com.azure.communication.phonenumbers.siprouting.models.SipTrunkRoute;
import com.azure.core.http.HttpClient;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@Execution(value = ExecutionMode.SAME_THREAD)
public class SipRoutingClientIntegrationTest extends SipRoutingIntegrationTestBase {

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void getTrunks(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "getTrunksSync");
        assertNotNull(client.listTrunks());
    }

//    @ParameterizedTest
//    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void getPurchasedPhoneNumberWithAAD(HttpClient httpClient) {
        SipRoutingClient client = getClientWithManagedIdentity(httpClient, "getTrunksWithAADSync");
        assertNotNull(client.listTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void getTrunksWithResponse(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "getTrunksWithResponse");
        Response<List<SipTrunk>> response = client.listTrunksWithResponse(Context.NONE);
        assertNotNull(response);
        assertNotNull(response.getValue());
        assertEquals(200, response.getStatusCode());
        List<SipTrunk> trunks = response.getValue();
        assertNotNull(trunks);
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setTrunks(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setTrunksSync");
        List<SipTrunk> expectedTrunks = asList(
            new SipTrunk("1.fqdn.com", 1234),
            new SipTrunk("2.fqdn.com", 2345),
            new SipTrunk("3.fqdn.com", 3456)
        );
        client.setTrunks(expectedTrunks);
        validateTrunks(expectedTrunks, client.listTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setTrunksWithoutAffectingRoutes(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setTrunksWithoutAffectingRoutes");
        List<SipTrunkRoute> expectedRoutes = asList(
            new SipTrunkRoute("route0", "0.*").setDescription("desc0"),
            new SipTrunkRoute("route1", "1.*").setDescription("desc1"),
            new SipTrunkRoute("route2", "2.*").setDescription("desc2")
        );
        client.setRoutes(expectedRoutes);

        List<SipTrunk> expectedTrunks = asList(
            new SipTrunk("1.fqdn.com", 1234),
            new SipTrunk("2.fqdn.com", 2345),
            new SipTrunk("3.fqdn.com", 3456)
        );
        client.setTrunks(expectedTrunks);

        validateTrunks(expectedTrunks, client.listTrunks());
        validateRoutes(client.listRoutes());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setTrunksWithResponse(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setTrunksWithResponse");
        List<SipTrunk> expectedTrunks = asList(
            new SipTrunk("1.fqdn.com", 1234),
            new SipTrunk("2.fqdn.com", 2345),
            new SipTrunk("3.fqdn.com", 3456)
        );
        Response<Void> response = client.setTrunksWithResponse(expectedTrunks, Context.NONE);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        validateTrunks(expectedTrunks, client.listTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setTrunk(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setTrunk");
        client.setTrunk(new SipTrunk("4.fqdn.com", 4567));
//        validateTrunks(client.getTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setTrunkWithResponse(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setTrunkWithResponse");
        SipTrunk trunk = new SipTrunk("4.fqdn.com", 4567);
        client.setTrunk(trunk);
        Response<Void> response = client.setTrunkWithResponse(trunk, Context.NONE);
        assertNotNull(response);
        assertNull(response.getValue());
        assertEquals(200, response.getStatusCode());
//        validateTrunks(expectedTrunks, client.getTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void deleteTrunk(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "deleteTrunk");
        client.setTrunk(new SipTrunk("delete.fqdn.com", 5678));
        client.deleteTrunk("delete.fqdn.com");
//        validateTrunks(client.getTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void deleteTrunkWithResponse(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "deleteTrunkWithResponse");
        client.setTrunk(new SipTrunk("delete.fqdn.com", 5678));
        Response<Void> response = client.deleteTrunkWithResponse("delete.fqdn.com", Context.NONE);
        assertNotNull(response);
        assertNull(response.getValue());
        assertEquals(200, response.getStatusCode());
//        validateTrunks(expectedTrunks, client.getTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void listRoutes(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "listRoutesSync");
        assertNotNull(client.listRoutes());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void listRoutesWithResponse(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "listRoutesWithResponse");
        Response<List<SipTrunkRoute>> response = client.listRoutesWithResponse(Context.NONE);
        assertNotNull(response);
        assertNotNull(response.getValue());
        assertEquals(200, response.getStatusCode());
        List<SipTrunkRoute> routes = response.getValue();
        assertNotNull(routes);
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setRoutes(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setRoutesSync");
        client.setRoutes(asList(
            new SipTrunkRoute("route0", "0.*").setDescription("desc0"),
            new SipTrunkRoute("route1", "1.*").setDescription("desc1"),
            new SipTrunkRoute("route2", "2.*").setDescription("desc2")
        ));
        validateRoutes(client.listRoutes());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setRoutesWithoutAffectingTrunks(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setRoutesWithoutAffectingTrunks");
        List<SipTrunk> expectedTrunks = asList(
            new SipTrunk("1.fqdn.com", 1234),
            new SipTrunk("2.fqdn.com", 2345),
            new SipTrunk("3.fqdn.com", 3456)
        );
        client.setTrunks(expectedTrunks);

        List<SipTrunkRoute> expectedRoutes = asList(
            new SipTrunkRoute("route0", "0.*").setDescription("desc0"),
            new SipTrunkRoute("route1", "1.*").setDescription("desc1"),
            new SipTrunkRoute("route2", "2.*").setDescription("desc2")
        );
        client.setRoutes(expectedRoutes);

        validateRoutes(client.listRoutes());
        validateTrunks(expectedTrunks, client.listTrunks());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void setRoutesWithResponse(HttpClient httpClient) {
        SipRoutingClient client = getClientWithConnectionString(httpClient, "setRoutesWithResponse");
        Response<Void> response = client.setRoutesWithResponse(asList(
            new SipTrunkRoute("route0", "0.*").setDescription("desc0"),
            new SipTrunkRoute("route1", "1.*").setDescription("desc1"),
            new SipTrunkRoute("route2", "2.*").setDescription("desc2")
        ), Context.NONE);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        validateRoutes(client.listRoutes());
    }

//    @ParameterizedTest
//    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
//    public void setRoute(HttpClient httpClient) {
//        SipRoutingClient client = getClientWithConnectionString(httpClient, "setRouteSync");
//        TrunkRoute route = client.setRoute(new TrunkRoute().setName("route3").setNumberPattern("0.*").setDescription("desc3"));
//        assertEquals("route3", route.getName());
//        assertEquals("desc3", route.getDescription());
//        assertEquals("0.*", route.getNumberPattern());
//        assertTrue(route.getTrunks().isEmpty());
//    }

//    @ParameterizedTest
//    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
//    public void setRouteWithResponse(HttpClient httpClient) {
//        SipRoutingClient client = getClientWithConnectionString(httpClient, "setRouteWithResponse");
//        Response<TrunkRoute> response = client.setRouteWithResponse(
//            new TrunkRoute().setName("route3").setNumberPattern("0.*").setDescription("desc3"), Context.NONE);
//        assertNotNull(response);
//        assertNotNull(response.getValue());
//        assertEquals(200, response.getStatusCode());
//        TrunkRoute route = response.getValue();
//        assertEquals("route3", route.getName());
//        assertEquals("0.*", route.getNumberPattern());
//        assertEquals("desc3", route.getDescription());
//        assertTrue(route.getTrunks().isEmpty());
//    }

//    @ParameterizedTest
//    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
//    public void deleteRoute(HttpClient httpClient) {
//        SipRoutingClient client = getClientWithConnectionString(httpClient, "deleteRoute");
//        TrunkRoute route = client.setRoute(new TrunkRoute().setName("delRoute").setNumberPattern(".*").setDescription("delDesc"));
//        TrunkRoute deletedRoute = client.deleteRoute("delRoute");
//
//        assertNotNull(deletedRoute);
//        assertEquals("delRoute", deletedRoute.getName());
//        assertEquals(".*", deletedRoute.getNumberPattern());
//        assertEquals("delDesc", deletedRoute.getDescription());
//        assertTrue(deletedRoute.getTrunks().isEmpty());
//    }
//
//    @ParameterizedTest
//    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
//    public void deleteRouteWithResponse(HttpClient httpClient) {
//        SipRoutingClient client = getClientWithConnectionString(httpClient, "deleteRouteWithResponse");
//        TrunkRoute route = client.setRoute(new TrunkRoute().setName("delRoute").setNumberPattern(".*").setDescription("delDesc"));
//        Response<TrunkRoute> response = client.deleteRouteWithResponse("delRoute", Context.NONE);
//        assertNotNull(response);
//        assertNotNull(response.getValue());
//        assertEquals(200, response.getStatusCode());
//        TrunkRoute deletedRoute = response.getValue();
//
//        assertEquals("delRoute", deletedRoute.getName());
//        assertEquals(".*", deletedRoute.getNumberPattern());
//        assertEquals("delDesc", deletedRoute.getDescription());
//        assertTrue(deletedRoute.getTrunks().isEmpty());
//    }

    private void validateTrunks(List<SipTrunk> expected, List<SipTrunk> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (SipTrunk expectedTrunk : expected) {
            Optional<SipTrunk> actualTrunk = actual.stream()
                .filter(value -> Objects.equals(expectedTrunk.getFqdn(), value.getFqdn())).findAny();
            assertTrue(actualTrunk.isPresent());
            assertEquals(expectedTrunk.getSipSignalingPort(), actualTrunk.get().getSipSignalingPort());
        }
    }

    private void validateRoutes(List<SipTrunkRoute> routes) {
        assertNotNull(routes);
        assertEquals(3, routes.size());
        for (int i = 0; i < routes.size(); i++) {
            SipTrunkRoute route = routes.get(i);
            assertEquals("route" + i, route.getName());
            assertEquals(i + ".*", route.getNumberPattern());
            assertEquals("desc" + i, route.getDescription());
            assertTrue(route.getTrunks().isEmpty());
        }
    }

/*
    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void getPurchasedPhoneNumberWithAAD(HttpClient httpClient) {
    }


    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void getPurchasedPhoneNumberWithResponse(HttpClient httpClient) {
//        String phoneNumber = getTestPhoneNumber(PHONE_NUMBER);
        Response<PurchasedPhoneNumber> response = this.getClientWithConnectionString(httpClient, "getPurchasedPhoneNumberWithResponseSync")
            .getPurchasedPhoneNumberWithResponse(phoneNumber, Context.NONE);
        PurchasedPhoneNumber number = response.getValue();
        assertEquals(200, response.getStatusCode());
        assertEquals(phoneNumber, number.getPhoneNumber());
        assertEquals(COUNTRY_CODE, number.getCountryCode());
    }


    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void listPurchasedPhoneNumbers(HttpClient httpClient) {
        PagedIterable<PurchasedPhoneNumber> numbers = this.getClientWithConnectionString(httpClient, "listPurchasedPhoneNumbersSync").listPurchasedPhoneNumbers(Context.NONE);
        PurchasedPhoneNumber number = numbers.iterator().next();
        assertNotNull(number.getPhoneNumber());
        assertEquals(COUNTRY_CODE, number.getCountryCode());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    public void listPurchasedPhoneNumbersWithoutContext(HttpClient httpClient) {
        PagedIterable<PurchasedPhoneNumber> numbers = this.getClientWithConnectionString(httpClient, "listPurchasedPhoneNumbersSync").listPurchasedPhoneNumbers();
        PurchasedPhoneNumber number = numbers.iterator().next();
        assertNotNull(number.getPhoneNumber());
        assertEquals(COUNTRY_CODE, number.getCountryCode());
    }*/

/*    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    @DisabledIfEnvironmentVariable(
        named = "COMMUNICATION_SKIP_INT_PHONENUMBERS_TEST",
        matches = "(?i)(true)")
    public void beginSearchAvailablePhoneNumbers(HttpClient httpClient) {
        SyncPoller<PhoneNumberOperation, PhoneNumberSearchResult> poller = beginSearchAvailablePhoneNumbersHelper(httpClient, "beginSearchAvailablePhoneNumbersSync", true);
        PollResponse<PhoneNumberOperation> response = poller.waitForCompletion();
        if (LongRunningOperationStatus.SUCCESSFULLY_COMPLETED == response.getStatus()) {
            PhoneNumberSearchResult searchResult = poller.getFinalResult();
            assertEquals(searchResult.getPhoneNumbers().size(), 1);
            assertNotNull(searchResult.getSearchId());
        } else {
            fail("Long Running Operation Status was not successfully completed");
        }
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    @DisabledIfEnvironmentVariable(
        named = "SKIP_LIVE_TEST",
        matches = "(?i)(true)")
    public void beginPurchaseandReleasePhoneNumbersWithoutContext(HttpClient httpClient) {
        SyncPoller<PhoneNumberOperation, PhoneNumberSearchResult> poller = beginSearchAvailablePhoneNumbersHelper(httpClient, "beginPurchaseandReleasePhoneNumbers_beginSearchAvailablePhoneNumbersWithoutContextSync", false);
        PollResponse<PhoneNumberOperation> response = poller.waitForCompletion();
        if (LongRunningOperationStatus.SUCCESSFULLY_COMPLETED == response.getStatus()) {
            PhoneNumberSearchResult searchResult = poller.getFinalResult();
//            String phoneNumber = getTestPhoneNumber(searchResult.getPhoneNumbers().get(0));
            PollResponse<PhoneNumberOperation> purchaseOperationResponse = beginPurchasePhoneNumbersHelper(httpClient, searchResult.getSearchId(), "beginPurchasePhoneNumbersWithoutContextSync", false).waitForCompletion();
            assertEquals(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED, purchaseOperationResponse.getStatus());
            PollResponse<PhoneNumberOperation> releaseOperationResponse = beginReleasePhoneNumberHelper(httpClient, phoneNumber, "beginReleasePhoneNumberWithoutContextSync", false).waitForCompletion();
            assertEquals(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED, releaseOperationResponse.getStatus());
        } else {
            fail("Long Running Operation Status was not successfully completed");
        }
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    @DisabledIfEnvironmentVariable(
        named = "SKIP_LIVE_TEST",
        matches = "(?i)(true)")
    public void beginPurchaseandReleasePhoneNumbers(HttpClient httpClient) {
        SyncPoller<PhoneNumberOperation, PhoneNumberSearchResult> poller = beginSearchAvailablePhoneNumbersHelper(httpClient, "beginPurchaseandReleasePhoneNumbers_beginSearchAvailablePhoneNumbersSync", true);
        PollResponse<PhoneNumberOperation> response = poller.waitForCompletion();
        if (LongRunningOperationStatus.SUCCESSFULLY_COMPLETED == response.getStatus()) {
            PhoneNumberSearchResult searchResult = poller.getFinalResult();
//            String phoneNumber = getTestPhoneNumber(searchResult.getPhoneNumbers().get(0));
            PollResponse<PhoneNumberOperation> purchaseOperationResponse = beginPurchasePhoneNumbersHelper(httpClient, searchResult.getSearchId(), "beginPurchasePhoneNumbersSync", true).waitForCompletion();
            assertEquals(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED, purchaseOperationResponse.getStatus());
            PollResponse<PhoneNumberOperation> releaseOperationResponse = beginReleasePhoneNumberHelper(httpClient, phoneNumber, "beginReleasePhoneNumberSync", true).waitForCompletion();
            assertEquals(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED, releaseOperationResponse.getStatus());
        } else {
            fail("Long Running Operation Status was not successfully completed");
        }
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    @DisabledIfEnvironmentVariable(
        named = "COMMUNICATION_SKIP_INT_PHONENUMBERS_TEST",
        matches = "(?i)(true)")
    public void beginUpdatePhoneNumberCapabilitiesWithoutContext(HttpClient httpClient) {
//        String phoneNumber = getTestPhoneNumber(PHONE_NUMBER);
        PollResponse<PhoneNumberOperation> result = beginUpdatePhoneNumberCapabilitiesHelper(httpClient, phoneNumber, "beginUpdatePhoneNumberCapabilitiesWithoutContextSync", false).waitForCompletion();
        assertEquals(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED, result.getStatus());
        assertEquals(PhoneNumberOperationStatus.SUCCEEDED, result.getValue().getStatus());
    }

    @ParameterizedTest
    @MethodSource("com.azure.core.test.TestBase#getHttpClients")
    @DisabledIfEnvironmentVariable(
        named = "COMMUNICATION_SKIP_INT_PHONENUMBERS_TEST",
        matches = "(?i)(true)")
    public void beginUpdatePhoneNumberCapabilities(HttpClient httpClient) {
        String phoneNumber = getTestPhoneNumber(PHONE_NUMBER);
        PollResponse<PhoneNumberOperation> result = beginUpdatePhoneNumberCapabilitiesHelper(httpClient, phoneNumber, "beginUpdatePhoneNumberCapabilities", true).waitForCompletion();
        assertEquals(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED, result.getStatus());
        assertEquals(PhoneNumberOperationStatus.SUCCEEDED, result.getValue().getStatus());
    }*/

/*    private SyncPoller<PhoneNumberOperation, PhoneNumberSearchResult> beginSearchAvailablePhoneNumbersHelper(HttpClient httpClient, String testName, boolean withContext) {
        PhoneNumberCapabilities capabilities = new PhoneNumberCapabilities();
        capabilities.setCalling(PhoneNumberCapabilityType.INBOUND);
        capabilities.setSms(PhoneNumberCapabilityType.INBOUND_OUTBOUND);
        PhoneNumberSearchOptions searchOptions = new PhoneNumberSearchOptions().setQuantity(1);

        if (withContext) {
            return setPollInterval(getClientWithConnectionString(httpClient, testName).beginSearchAvailablePhoneNumbers(
                COUNTRY_CODE,
                PhoneNumberType.TOLL_FREE,
                PhoneNumberAssignmentType.APPLICATION,
                capabilities,
                searchOptions,
                Context.NONE));
        }
        return setPollInterval(getClientWithConnectionString(httpClient, testName).beginSearchAvailablePhoneNumbers(
            COUNTRY_CODE,
            PhoneNumberType.TOLL_FREE,
            PhoneNumberAssignmentType.APPLICATION,
            capabilities));
    }

    private SyncPoller<PhoneNumberOperation, PurchasePhoneNumbersResult> beginPurchasePhoneNumbersHelper(HttpClient httpClient, String searchId, String testName, boolean withContext) {
        if (withContext) {
            return setPollInterval(this.getClientWithConnectionString(httpClient, testName)
            .beginPurchasePhoneNumbers(searchId, Context.NONE));
        }
        return setPollInterval(this.getClientWithConnectionString(httpClient, testName)
        .beginPurchasePhoneNumbers(searchId));
    }

    private SyncPoller<PhoneNumberOperation, ReleasePhoneNumberResult> beginReleasePhoneNumberHelper(HttpClient httpClient, String phoneNumber, String testName, boolean withContext) {
        if (getTestMode() == TestMode.PLAYBACK) {
            phoneNumber = "+REDACTED";
        }
        if (withContext) {
            return setPollInterval(this.getClientWithConnectionString(httpClient, testName)
            .beginReleasePhoneNumber(phoneNumber, Context.NONE));
        }
        return setPollInterval(this.getClientWithConnectionString(httpClient, testName)
        .beginReleasePhoneNumber(phoneNumber));
    }

    private SyncPoller<PhoneNumberOperation, PurchasedPhoneNumber>
        beginUpdatePhoneNumberCapabilitiesHelper(HttpClient httpClient, String phoneNumber, String testName, boolean withContext) {
        PhoneNumbersClient client = this.getClientWithConnectionString(httpClient, testName);
        Response<PurchasedPhoneNumber> responseAcquiredPhone = client.getPurchasedPhoneNumberWithResponse(phoneNumber, Context.NONE);
        PhoneNumberCapabilities capabilities = new PhoneNumberCapabilities();
        capabilities.setCalling(responseAcquiredPhone.getValue().getCapabilities().getCalling() == PhoneNumberCapabilityType.INBOUND ? PhoneNumberCapabilityType.OUTBOUND : PhoneNumberCapabilityType.INBOUND);
        capabilities.setSms(responseAcquiredPhone.getValue().getCapabilities().getSms() == PhoneNumberCapabilityType.INBOUND_OUTBOUND ? PhoneNumberCapabilityType.OUTBOUND : PhoneNumberCapabilityType.INBOUND_OUTBOUND);
        if (withContext) {
            return setPollInterval(client.beginUpdatePhoneNumberCapabilities(phoneNumber, capabilities, Context.NONE));
        }
        return setPollInterval(client.beginUpdatePhoneNumberCapabilities(phoneNumber, capabilities));
    }

    private <T, U> SyncPoller<T, U> setPollInterval(SyncPoller<T, U> syncPoller) {
        return interceptorManager.isPlaybackMode()
            ? syncPoller.setPollInterval(Duration.ofMillis(1))
            : syncPoller.setPollInterval(Duration.ofSeconds(1));
    }*/

    private SipRoutingClient getClientWithConnectionString(HttpClient httpClient, String testName) {
        SipRoutingClientBuilder builder = super.getClientBuilderWithConnectionString(httpClient);
        return addLoggingPolicy(builder, testName).buildClient();
    }

    private SipRoutingClient getClientWithManagedIdentity(HttpClient httpClient, String testName) {
        SipRoutingClientBuilder builder = super.getClientBuilderUsingManagedIdentity(httpClient);
        return addLoggingPolicy(builder, testName).buildClient();
    }
}
