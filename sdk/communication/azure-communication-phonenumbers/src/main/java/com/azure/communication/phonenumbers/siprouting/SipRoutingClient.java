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
import com.azure.core.util.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Client for SIP routing configuration.
 */
@ServiceClient(builder = SipRoutingClientBuilder.class)
public final class SipRoutingClient {
    private final SipRoutingAdminClientImpl client;

    SipRoutingClient(SipRoutingAdminClientImpl client) {
        this.client = client;
    }

    /**
     * Lists SIP Trunks.
     *
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<SipTrunk> listTrunks() {
        return SipTrunkConverter.convert(getSipConfiguration().getTrunks());
    }

    /**
     * Lists SIP Trunks.
     *
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<SipTrunk>> listTrunksWithResponse(Context context) {
        return client.getSipConfigurationWithResponseAsync(context)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .map(result -> new SimpleResponse<>(result, SipTrunkConverter.convert(result.getValue().getTrunks())))
            .block();
    }

    /**
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<SipTrunk> setTrunks(List<SipTrunk> trunks) {
        SipConfiguration update = new SipConfiguration().setTrunks(SipTrunkConverter.convert(trunks));
        List<String> storedFqdns = listTrunks().stream().map(SipTrunk::getFqdn).collect(Collectors.toList());
        Set<String> updatedFqdns = trunks.stream().map(SipTrunk::getFqdn).collect(Collectors.toSet());
        for (String storedFqdn : storedFqdns) {
            if (!updatedFqdns.contains(storedFqdn)) {
                update.getTrunks().put(storedFqdn, null);
            }
        }

        return SipTrunkConverter.convert(setSipConfiguration(update).getTrunks());
    }

    /**
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<SipTrunk>> setTrunksWithResponse(List<SipTrunk> trunks, Context context) {
        SipConfiguration update = new SipConfiguration().setTrunks(SipTrunkConverter.convert(trunks));
        List<String> storedFqdns = listTrunks().stream().map(SipTrunk::getFqdn).collect(Collectors.toList());
        Set<String> updatedFqdns = trunks.stream().map(SipTrunk::getFqdn).collect(Collectors.toSet());
        for (String storedFqdn : storedFqdns) {
            if (!updatedFqdns.contains(storedFqdn)) {
                update.getTrunks().put(storedFqdn, null);
            }
        }

        return client.patchSipConfigurationWithResponseAsync(update, context)
            .map(result -> new SimpleResponse<>(result, SipTrunkConverter.convert(result.getValue().getTrunks())))
            .block();
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void setTrunk(SipTrunk trunk) {
        Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunks = new HashMap<>();
        trunks.put(trunk.getFqdn(), SipTrunkConverter.convert(trunk));
        client.patchSipConfigurationAsync(new SipConfiguration().setTrunks(trunks))
            .map(result -> {
                List<SipTrunk> filteredTrunks = SipTrunkConverter.convert(result.getTrunks()).stream()
                    .filter(value -> value.getFqdn().equals(trunk.getFqdn()))
                    .collect(Collectors.toList());
                return filteredTrunks.isEmpty() ? null : filteredTrunks.get(0);
            }).block();
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> setTrunkWithResponse(SipTrunk trunk, Context context) {
        Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunks = new HashMap<>();
        trunks.put(trunk.getFqdn(), SipTrunkConverter.convert(trunk));
        SipConfiguration update = new SipConfiguration().setTrunks(trunks);
        return client.patchSipConfigurationWithResponseAsync(update, context)
            .map(result -> new SimpleResponse<Void>(result, null)).block();
    }

    /**
     * Deletes SIP Trunk.
     *
     * @param fqdn SIP Trunk FQDN.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteTrunk(String fqdn) {
        List<SipTrunk> trunks = listTrunks();
        List<SipTrunk> deletedTrunks = trunks.stream()
            .filter(trunk -> fqdn.equals(trunk.getFqdn()))
            .collect(Collectors.toList());

        if (!deletedTrunks.isEmpty()) {
            Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunksUpdate = new HashMap<>();
            trunksUpdate.put(fqdn, null);
            client.patchSipConfiguration(new SipConfiguration().setTrunks(trunksUpdate));
        }
    }

    /**
     * Deletes SIP Trunk.
     *
     * @param fqdn SIP Trunk FQDN.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> deleteTrunkWithResponse(String fqdn, Context context) {
        List<SipTrunk> trunks = listTrunks();
        List<SipTrunk> deletedTrunks = trunks.stream().filter(trunk -> fqdn.equals(trunk.getFqdn()))
            .collect(Collectors.toList());

        if (!deletedTrunks.isEmpty()) {
            Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.SipTrunk> trunksUpdate = new HashMap<>();
            trunksUpdate.put(fqdn, null);
            return client.patchSipConfigurationWithResponseAsync(new SipConfiguration().setTrunks(trunksUpdate), context)
                .map(result -> new SimpleResponse<Void>(result, null)).block();
        }
        return null;
    }

    /**
     * Lists SIP Trunk Routes.
     *
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<SipTrunkRoute> listRoutes() {
        return getSipConfiguration().getRoutes();
    }

    /**
     * Lists SIP Trunk Routes.
     *
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<SipTrunkRoute>> listRoutesWithResponse(Context context) {
        return client.getSipConfigurationWithResponseAsync(context)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .map(result -> new SimpleResponse<>(result, result.getValue().getRoutes()))
            .block();
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<SipTrunkRoute> setRoutes(List<SipTrunkRoute> routes) {
        return setSipConfiguration(new SipConfiguration().setRoutes(routes)).getRoutes();
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<SipTrunkRoute>> setRoutesWithResponse(List<SipTrunkRoute> routes, Context context) {
        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration().setRoutes(routes), context)
            .map(result -> new SimpleResponse<>(result, result.getValue().getRoutes()))
            .block();
    }

    private SipConfiguration getSipConfiguration() {
        return client.getSipConfigurationAsync()
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .block();
    }

    private SipConfiguration setSipConfiguration(SipConfiguration update) {
        return client.patchSipConfigurationAsync(update)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .block();
    }

    private SipRoutingResponseException translateException(CommunicationErrorResponseException exception) {
        SipRoutingError error = null;
        if (exception.getValue() != null) {
            error = SipRoutingErrorConverter.convert(exception.getValue().getError());
        }
        return new SipRoutingResponseException(exception.getMessage(), exception.getResponse(), error);
    }
}
