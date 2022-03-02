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
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;

import java.util.List;
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
     * Gets SIP Trunks.
     *
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<Trunk>> getTrunksWithResponse(Context context) {
        return client.getSipConfigurationWithResponseAsync(context)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .map(result -> new SimpleResponse<>(result, TrunkConverter.convert(result.getValue().getTrunks())))
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
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<Trunk>> setTrunksWithResponse(List<Trunk> trunks, Context context) {
        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(TrunkConverter.convert(trunks)), context)
            .map(result -> new SimpleResponse<>(result, TrunkConverter.convert(result.getValue().getTrunks())))
            .block();
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Trunk setTrunk(Trunk trunk) {
        List<Trunk> trunks = getTrunks();
        Integer setIndex = findIndex(trunks, trunk);
        if (setIndex != null) {
            trunks.set(setIndex, trunk);
            return setTrunks(trunks).get(setIndex);
        } else {
            trunks.add(trunk);
            // todo
            return setTrunks(trunks).get(trunks.size() - 1);
        }
    }

    /**
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Trunk> setTrunkWithResponse(Trunk trunk, Context context) {
        List<Trunk> trunks = getTrunks();
        Integer setIndex = findIndex(trunks, trunk);
        if (setIndex != null) {
            trunks.set(setIndex, trunk);
        } else {
            trunks.add(trunk);
        }

        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(TrunkConverter.convert(trunks)), context)
            .map(result -> new SimpleResponse<>(result, trunk))
            .block();
    }

    /**
     * Deletes SIP Trunk.
     *
     * @param fqdn SIP Trunk FQDN.
     * @return deleted SIP Trunk or null.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Trunk deleteTrunk(String fqdn) {
        List<Trunk> trunks = getTrunks();
        List<Trunk> deletedTrunks = trunks.stream().filter(trunk -> fqdn.equals(trunk.getFqdn()))
            .collect(Collectors.toList());

        if (!deletedTrunks.isEmpty()) {
            setTrunks(trunks.stream().filter(trunk -> !fqdn.equals(trunk.getFqdn()))
                .collect(Collectors.toList()));
            return deletedTrunks.get(0);
        }

        return null;
    }

    /**
     * Deletes SIP Trunk.
     *
     * @param trunk SIP Trunk.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Trunk> deleteTrunkWithResponse(Trunk trunk, Context context) {
        List<Trunk> trunks = getTrunks();
        Integer deleteIndex = findIndex(trunks, trunk);

        if (deleteIndex != null) {
            Trunk removedTrunk = trunks.remove((int)deleteIndex);
            setTrunksWithResponse(trunks, context);

            return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(TrunkConverter.convert(trunks)), context)
                .map(result -> new SimpleResponse<>(result, removedTrunk))
                .block();
        }
        return null;
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

    /**
     * Gets SIP Trunk Routes.
     *
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<TrunkRoute>> getRoutesWithResponse(Context context) {
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
    public List<TrunkRoute> setRoutes(List<TrunkRoute> routes) {
        SipConfiguration configuration = setSipConfiguration(new SipConfiguration(routes));
        return configuration.getRoutes();
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<List<TrunkRoute>> setRoutesWithResponse(List<TrunkRoute> routes, Context context) {
        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(routes), context)
            .map(result -> new SimpleResponse<>(result, result.getValue().getRoutes()))
            .block();
    }

    /**
     * Sets SIP Trunk Route.
     * If a route with specified name already exists, it will be replaced, otherwise a new route will be added.
     *
     * @param route SIP Trunk Route.
     * @return SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TrunkRoute setRoute(TrunkRoute route) {
        List<TrunkRoute> routes = getRoutes();
        Integer setIndex = findIndex(routes, route);
        if (setIndex != null) {
            routes.set(setIndex, route);
            return setRoutes(routes).get(setIndex);
        } else {
            routes.add(route);
            // todo
            return setRoutes(routes).get(routes.size() - 1);
        }
    }

    /**
     * Sets SIP Trunk Route.
     * If a route with specified name already exists, it will be replaced, otherwise a new route will be added.
     *
     * @param route SIP Trunk Route.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Route.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<TrunkRoute> setRouteWithResponse(TrunkRoute route, Context context) {
        List<TrunkRoute> routes = getRoutes();
        Integer setIndex = findIndex(routes, route);
        if (setIndex != null) {
            routes.set(setIndex, route);
        } else {
            routes.add(route);
        }

        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(routes), context)
            .map(result -> new SimpleResponse<>(result, route))
            .block();
    }

    /**
     * Deletes SIP Trunk Route.
     *
     * @param name SIP Trunk Route name.
     * @return deleted SIP Trunk Route or null.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TrunkRoute deleteRoute(String name) {
        List<TrunkRoute> routes = getRoutes();
        List<TrunkRoute> deletedRoutes = routes.stream().filter(route -> name.equals(route.getName()))
            .collect(Collectors.toList());

        if (!deletedRoutes.isEmpty()) {
            setRoutes(routes.stream().filter(route -> !name.equals(route.getName()))
                .collect(Collectors.toList()));
            return deletedRoutes.get(0);
        }

        return null;
    }

    /**
     * Deletes SIP Trunk Route.
     *
     * @param route SIP Trunk Route.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Route.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<TrunkRoute> deleteRouteWithResponse(TrunkRoute route, Context context) {
        List<TrunkRoute> routes = getRoutes();
        Integer deleteIndex = findIndex(routes, route);

        if (deleteIndex != null) {
            TrunkRoute removedRoute = routes.remove((int)deleteIndex);
            setRoutesWithResponse(routes, context);

            return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(routes), context)
                .map(result -> new SimpleResponse<>(result, removedRoute))
                .block();
        }
        return null;
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
