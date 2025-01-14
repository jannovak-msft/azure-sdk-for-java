// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicesbackup.generated;

import com.azure.core.credential.AccessToken;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.resourcemanager.recoveryservicesbackup.RecoveryServicesBackupManager;
import com.azure.resourcemanager.recoveryservicesbackup.models.CreateMode;
import com.azure.resourcemanager.recoveryservicesbackup.models.ProtectedItemResource;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class BackupProtectedItemsListMockTests {
    @Test
    public void testList() throws Exception {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        ArgumentCaptor<HttpRequest> httpRequest = ArgumentCaptor.forClass(HttpRequest.class);

        String responseStr =
            "{\"value\":[{\"properties\":{\"protectedItemType\":\"ProtectedItem\",\"backupManagementType\":\"AzureBackupServer\",\"workloadType\":\"SystemState\",\"containerName\":\"l\",\"sourceResourceId\":\"lxpnovyoanf\",\"policyId\":\"swqagywvtxigvjrk\",\"lastRecoveryPoint\":\"2020-12-28T20:40:15Z\",\"backupSetName\":\"eukyaw\",\"createMode\":\"Invalid\",\"deferredDeleteTimeInUTC\":\"2021-09-30T02:18:30Z\",\"isScheduledForDeferredDelete\":false,\"deferredDeleteTimeRemaining\":\"u\",\"isDeferredDeleteScheduleUpcoming\":true,\"isRehydrate\":true,\"resourceGuardOperationRequests\":[\"dsjtdlpbni\"],\"isArchiveEnabled\":false,\"policyName\":\"zlsvbzfcpuoeed\",\"softDeleteRetentionPeriodInDays\":1674449471},\"eTag\":\"iklhsyekrdrenx\",\"location\":\"lriyehqbe\",\"tags\":{\"ytjlkesmmpath\":\"lhydwbdbfgrlpu\"},\"id\":\"btahdeanii\",\"name\":\"llbvgwzsfftedous\",\"type\":\"ktjtgra\"}]}";

        Mockito.when(httpResponse.getStatusCode()).thenReturn(200);
        Mockito.when(httpResponse.getHeaders()).thenReturn(new HttpHeaders());
        Mockito
            .when(httpResponse.getBody())
            .thenReturn(Flux.just(ByteBuffer.wrap(responseStr.getBytes(StandardCharsets.UTF_8))));
        Mockito
            .when(httpResponse.getBodyAsByteArray())
            .thenReturn(Mono.just(responseStr.getBytes(StandardCharsets.UTF_8)));
        Mockito
            .when(httpClient.send(httpRequest.capture(), Mockito.any()))
            .thenReturn(
                Mono
                    .defer(
                        () -> {
                            Mockito.when(httpResponse.getRequest()).thenReturn(httpRequest.getValue());
                            return Mono.just(httpResponse);
                        }));

        RecoveryServicesBackupManager manager =
            RecoveryServicesBackupManager
                .configure()
                .withHttpClient(httpClient)
                .authenticate(
                    tokenRequestContext -> Mono.just(new AccessToken("this_is_a_token", OffsetDateTime.MAX)),
                    new AzureProfile("", "", AzureEnvironment.AZURE));

        PagedIterable<ProtectedItemResource> response =
            manager
                .backupProtectedItems()
                .list("mwwmjswenaww", "me", "leqioulndh", "yoeojhtollhs", com.azure.core.util.Context.NONE);

        Assertions.assertEquals("lriyehqbe", response.iterator().next().location());
        Assertions.assertEquals("lhydwbdbfgrlpu", response.iterator().next().tags().get("ytjlkesmmpath"));
        Assertions.assertEquals("l", response.iterator().next().properties().containerName());
        Assertions.assertEquals("lxpnovyoanf", response.iterator().next().properties().sourceResourceId());
        Assertions.assertEquals("swqagywvtxigvjrk", response.iterator().next().properties().policyId());
        Assertions
            .assertEquals(
                OffsetDateTime.parse("2020-12-28T20:40:15Z"),
                response.iterator().next().properties().lastRecoveryPoint());
        Assertions.assertEquals("eukyaw", response.iterator().next().properties().backupSetName());
        Assertions.assertEquals(CreateMode.INVALID, response.iterator().next().properties().createMode());
        Assertions
            .assertEquals(
                OffsetDateTime.parse("2021-09-30T02:18:30Z"),
                response.iterator().next().properties().deferredDeleteTimeInUtc());
        Assertions.assertEquals(false, response.iterator().next().properties().isScheduledForDeferredDelete());
        Assertions.assertEquals("u", response.iterator().next().properties().deferredDeleteTimeRemaining());
        Assertions.assertEquals(true, response.iterator().next().properties().isDeferredDeleteScheduleUpcoming());
        Assertions.assertEquals(true, response.iterator().next().properties().isRehydrate());
        Assertions
            .assertEquals(
                "dsjtdlpbni", response.iterator().next().properties().resourceGuardOperationRequests().get(0));
        Assertions.assertEquals(false, response.iterator().next().properties().isArchiveEnabled());
        Assertions.assertEquals("zlsvbzfcpuoeed", response.iterator().next().properties().policyName());
        Assertions.assertEquals(1674449471, response.iterator().next().properties().softDeleteRetentionPeriod());
        Assertions.assertEquals("iklhsyekrdrenx", response.iterator().next().etag());
    }
}
