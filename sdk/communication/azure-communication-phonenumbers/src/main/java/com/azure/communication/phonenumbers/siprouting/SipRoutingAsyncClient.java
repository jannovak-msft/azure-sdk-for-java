// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImpl;
import com.azure.communication.phonenumbers.siprouting.implementation.converters.SipRoutingErrorConverter;
import com.azure.communication.phonenumbers.siprouting.implementation.converters.SipTrunkConverter;
import com.azure.communication.phonenumbers.siprouting.implementation.models.CommunicationErrorResponseException;
import com.azure.communication.phonenumbers.siprouting.implementation.models.SipConfiguration;
import com.azure.communication.phonenumbers.siprouting.models.SipRoutingError;
import com.azure.communication.phonenumbers.siprouting.models.SipRoutingResponseException;
import com.azure.communication.phonenumbers.siprouting.models.SipTrunk;
import com.azure.communication.phonenumbers.siprouting.models.SipTrunkRoute;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Async client for SIP routing configuration.
 */
@ServiceClient(builder = SipRoutingClientBuilder.class, isAsync = true)
public final class SipRoutingAsyncClient {
    private final SipRoutingAdminClientImpl client;

    SipRoutingAsyncClient(SipRoutingAdminClientImpl client) {
        this.client = client;
    }

    /**
     * Lists SIP Trunks.
     *
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<SipTrunk>> listTrunks() {
        return getSipConfiguration().map(config -> SipTrunkConverter.convert(config.getTrunks()));
    }

    /**
     * Lists SIP Trunks.
     *
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<SipTrunk>>> listTrunksWithResponse() {
        return client.getSipConfigurationWithResponseAsync()
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .map(result -> new SimpleResponse<>(result, SipTrunkConverter.convert(result.getValue().getTrunks())));
    }

    /**
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<SipTrunk>> setTrunks(List<SipTrunk> trunks) {
        SipConfiguration update = new SipConfiguration().setTrunks(SipTrunkConverter.convert(trunks));
        return setSipConfiguration(update).map(config -> SipTrunkConverter.convert(config.getTrunks()));
    }

    /**
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<SipTrunk>>> setTrunksWithResponse(List<SipTrunk> trunks) {
        SipConfiguration update = new SipConfiguration().setTrunks(SipTrunkConverter.convert(trunks));
        return client.patchSipConfigurationWithResponseAsync(update)
            .map(result -> new SimpleResponse<>(result, SipTrunkConverter.convert(result.getValue().getTrunks())));
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @return void.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> setTrunk(SipTrunk trunk) {
        Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunks = new HashMap<>();
        trunks.put(trunk.getFqdn(), SipTrunkConverter.convert(trunk));
        return client.patchSipConfigurationAsync(new SipConfiguration().setTrunks(trunks)).then();
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @return Response object.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> setTrunkWithResponse(SipTrunk trunk) {
        Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunks = new HashMap<>();
        trunks.put(trunk.getFqdn(), SipTrunkConverter.convert(trunk));
        SipConfiguration update = new SipConfiguration().setTrunks(trunks);
        return client.patchSipConfigurationWithResponseAsync(update)
            .map(result -> new SimpleResponse<Void>(result, null));
    }

    /**
     * Deletes SIP Trunk.
     *
     * @param fqdn SIP Trunk FQDN.
     * @return void.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteTrunk(String fqdn) {
        List<SipTrunk> trunks = listTrunks().block();
        List<SipTrunk> deletedTrunks = trunks.stream()
            .filter(trunk -> fqdn.equals(trunk.getFqdn()))
            .collect(Collectors.toList());

        if (!deletedTrunks.isEmpty()) {
            Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunksUpdate = new HashMap<>();
            trunksUpdate.put(fqdn, null);
            return client.patchSipConfigurationAsync(new SipConfiguration().setTrunks(trunksUpdate)).then();
        }
        return Mono.empty();
    }

    /**
     * Deletes SIP Trunk.
     *
     * @param fqdn SIP Trunk FQDN.
     * @return Response object.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTrunkWithResponse(String fqdn) {
        List<SipTrunk> trunks = listTrunks().block();
        List<SipTrunk> deletedTrunks = trunks.stream().filter(trunk -> fqdn.equals(trunk.getFqdn()))
            .collect(Collectors.toList());

        if (!deletedTrunks.isEmpty()) {
            Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunksUpdate = new HashMap<>();
            trunksUpdate.put(fqdn, null);
            return client.patchSipConfigurationWithResponseAsync(new SipConfiguration().setTrunks(trunksUpdate))
                .map(result -> new SimpleResponse<>(result, null));
        }
        return Mono.empty();
    }

    /**
     * Lists SIP Trunk Routes.
     *
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<SipTrunkRoute>> listRoutes() {
        return getSipConfiguration().map(SipConfiguration::getRoutes);
    }

    /**
     * Lists SIP Trunk Routes.
     *
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<SipTrunkRoute>>> listRoutesWithResponse() {
        return client.getSipConfigurationWithResponseAsync()
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .map(result -> new SimpleResponse<>(result, result.getValue().getRoutes()));
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<SipTrunkRoute>> setRoutes(List<SipTrunkRoute> routes) {
        return setSipConfiguration(new SipConfiguration().setRoutes(routes)).map(SipConfiguration::getRoutes);
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<SipTrunkRoute>>> setRoutesWithResponse(List<SipTrunkRoute> routes) {
        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration().setRoutes(routes))
            .map(result -> new SimpleResponse<>(result, result.getValue().getRoutes()));
    }

    private Mono<SipConfiguration> getSipConfiguration() {
        return client.getSipConfigurationAsync()
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException);
    }

    private Mono<SipConfiguration> setSipConfiguration(SipConfiguration update) {
        return client.patchSipConfigurationAsync(update)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException);
    }

    private SipRoutingResponseException translateException(CommunicationErrorResponseException exception) {
        SipRoutingError error = null;
        if (exception.getValue() != null) {
            error = SipRoutingErrorConverter.convert(exception.getValue().getError());
        }
        return new SipRoutingResponseException(exception.getMessage(), exception.getResponse(), error);
    }
}
