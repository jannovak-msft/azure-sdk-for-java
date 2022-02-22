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
import com.azure.core.util.Context;

import java.util.List;
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
     * Gets SIP Trunks.
     *
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<Trunk> getTrunks() {
        SipConfiguration configuration = getSipConfiguration();
        return TrunkConverter.convert(configuration.getTrunks());
    }

    /**
     * Gets SIP Trunk Routes.
     *
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<TrunkRoute> getRoutes() {
        SipConfiguration configuration = getSipConfiguration();
        return configuration.getRoutes();
    }

    private SipConfiguration getSipConfiguration() {
        return client.getSipConfigurationAsync()
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .block();
    }

    /**
     * Gets SIP configuration.
     *
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<SipConfiguration> getSipConfigurationWithResponse(Context context) {
        return client.getSipConfigurationWithResponseAsync(context)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .block();
    }

    /**
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<Trunk> setTrunks(List<Trunk> trunks) {
        SipConfiguration configuration = setSipConfiguration(new SipConfiguration(TrunkConverter.convert(trunks)));
        return TrunkConverter.convert(configuration.getTrunks());
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<Trunk> setTrunk(Trunk trunk) {
        List<Trunk> trunks = getTrunks();
        Integer setIndex = findIndex(trunks, trunk);
        if (setIndex != null) {
            trunks.set(setIndex, trunk);
        } else {
            trunks.add(trunk);
        }

        return setTrunks(trunks);
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<TrunkRoute> setRoutes(List<TrunkRoute> routes) {
        SipConfiguration configuration = setSipConfiguration(new SipConfiguration(routes));
        return configuration.getRoutes();
    }

    /**
     * Sets SIP Trunk Route.
     * If a route with specified name already exists, it will be replaced, otherwise a new route will be added.
     *
     * @param route SIP Trunk Route.
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<TrunkRoute> setRoute(TrunkRoute route) {
        List<TrunkRoute> routes = getRoutes();
        Integer setIndex = findIndex(routes, route);
        if (setIndex != null) {
            routes.set(setIndex, route);
        } else {
            routes.add(route);
        }

        return setRoutes(routes);
    }

    /**
     * Updates SIP configuration.
     *
     * @param update the configuration update.
     * @return SIP configuration.
     */
    private SipConfiguration setSipConfiguration(SipConfiguration update) {
        return client.patchSipConfigurationAsync(update)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .block();
    }

    /**
     * Updates SIP configuration.
     *
     * @param update the configuration update.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<SipConfiguration> setSipConfigurationWithResponse(SipConfiguration update, Context context) {
        return client.patchSipConfigurationWithResponseAsync(update, context)
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
