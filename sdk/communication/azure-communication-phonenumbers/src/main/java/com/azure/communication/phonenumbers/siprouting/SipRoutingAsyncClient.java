// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImpl;
import com.azure.communication.phonenumbers.siprouting.implementation.converters.SipRoutingErrorConverter;
import com.azure.communication.phonenumbers.siprouting.implementation.converters.TrunkConverter;
import com.azure.communication.phonenumbers.siprouting.implementation.models.CommunicationErrorResponseException;
import com.azure.communication.phonenumbers.siprouting.implementation.models.SipConfiguration;
import com.azure.communication.phonenumbers.siprouting.models.SipRoutingError;
import com.azure.communication.phonenumbers.siprouting.models.SipRoutingResponseException;
import com.azure.communication.phonenumbers.siprouting.models.Trunk;
import com.azure.communication.phonenumbers.siprouting.models.TrunkRoute;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import reactor.core.publisher.Mono;

import java.util.List;

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
     * Gets SIP Trunks.
     *
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<Trunk>> getTrunks() {
        Mono<SipConfiguration> configuration = getSipConfiguration();
        return configuration.map(config -> TrunkConverter.convert(config.getTrunks()));
    }

    /**
     * Gets SIP configuration.
     *
     * @return Response object with the SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<SipConfiguration>> getSipConfigurationWithResponse() {
        return this.client.getSipConfigurationWithResponseAsync()
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException);
    }

    /**
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<Trunk>> setTrunks(List<Trunk> trunks) {
        Mono<SipConfiguration> configuration = setSipConfiguration(new SipConfiguration(TrunkConverter.convert(trunks)));
        return configuration.map(config -> TrunkConverter.convert(config.getTrunks()));
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<Trunk>> setTrunk(Trunk trunk) {
        List<Trunk> trunks = getTrunks().block();
        Integer setIndex = findIndex(trunks, trunk);
        if (setIndex != null) {
            trunks.set(setIndex, trunk);
        } else {
            trunks.add(trunk);
        }

        return setTrunks(trunks);
    }

    /**
     * Deletes SIP Trunk.
     *
     * @param trunk SIP Trunk.
     * @return deleted SIP Trunk or null.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Trunk> deleteTrunk(Trunk trunk) {
        List<Trunk> trunks = getTrunks().block();
        Integer deleteIndex = findIndex(trunks, trunk);

        if (deleteIndex != null) {
            final Trunk removedTrunk = trunks.remove((int)deleteIndex);
            return setTrunks(trunks).map(storedTrunks -> removedTrunk);
        }
        return null;
    }

    /**
     * Gets SIP Trunk Routes.
     *
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<TrunkRoute>> getRoutes() {
        Mono<SipConfiguration> configuration = getSipConfiguration();
        return configuration.map(SipConfiguration::getRoutes);
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<TrunkRoute>> setRoutes(List<TrunkRoute> routes) {
        Mono<SipConfiguration> configuration = setSipConfiguration(new SipConfiguration(routes));
        return configuration.map(SipConfiguration::getRoutes);
    }

    /**
     * Sets SIP Trunk Route.
     * If a route with specified name already exists, it will be replaced, otherwise a new route will be added.
     *
     * @param route SIP Trunk Route.
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<TrunkRoute>> setRoute(TrunkRoute route) {
        List<TrunkRoute> routes = getRoutes().block();
        Integer setIndex = findIndex(routes, route);
        if (setIndex != null) {
            routes.set(setIndex, route);
        } else {
            routes.add(route);
        }

        return setRoutes(routes);
    }

    /**
     * Deletes SIP Trunk Route.
     *
     * @param route SIP Trunk Route.
     * @return deleted SIP Trunk Route or null.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<TrunkRoute> deleteRoute(TrunkRoute route) {
        List<TrunkRoute> routes = getRoutes().block();
        Integer deleteIndex = findIndex(routes, route);

        if (deleteIndex != null) {
            final TrunkRoute removedRoute = routes.remove((int)deleteIndex);
            return setRoutes(routes).map(storedRoutes -> removedRoute);
        }
        return null;
    }

    /**
     * Updates SIP configuration.
     *
     * @param update the configuration update.
     * @return Response object with the SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<SipConfiguration>> setSipConfigurationWithResponse(SipConfiguration update) {
        return this.client.patchSipConfigurationWithResponseAsync(update)
            .onErrorMap(CommunicationErrorResponseException.class, e -> translateException(e));
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

    private Integer findIndex(List<TrunkRoute> routes, TrunkRoute route) {
        for (int i = 0, routesSize = routes.size(); i < routesSize; i++) {
            TrunkRoute storedRoute = routes.get(i);
            if (route.getName() != null && route.getName().equals(storedRoute.getName())) {
                return i;
            }
        }
        return null;
    }

    private Integer findIndex(List<Trunk> trunks, Trunk trunk) {
        for (int i = 0, trunksSize = trunks.size(); i < trunksSize; i++) {
            Trunk storedTrunk = trunks.get(i);
            if (trunk.getFqdn() != null && trunk.getFqdn().equals(storedTrunk.getFqdn())) {
                return i;
            }
        }
        return null;
    }
}
