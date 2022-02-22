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
     * Gets SIP Trunks.
     *
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<Trunk>>> getTrunksWithResponse(Context context) {
        return client.getSipConfigurationWithResponseAsync(context)
            .onErrorMap(CommunicationErrorResponseException.class, this::translateException)
            .map(result -> new SimpleResponse<>(result, TrunkConverter.convert(result.getValue().getTrunks())));
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
     * Sets SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<Trunk>>> setTrunksWithResponse(List<Trunk> trunks, Context context) {
        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(TrunkConverter.convert(trunks)), context)
            .map(result -> new SimpleResponse<>(result, TrunkConverter.convert(result.getValue().getTrunks())));
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
     * Sets SIP Trunk.
     * If a trunk with specified FQDN already exists, it will be replaced, otherwise a new trunk will be added.
     *
     * @param trunk SIP Trunk.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Trunk>> setTrunkWithResponse(Trunk trunk, Context context) {
        List<Trunk> trunks = getTrunks().block();
        Integer setIndex = findIndex(trunks, trunk);
        if (setIndex != null) {
            trunks.set(setIndex, trunk);
        } else {
            trunks.add(trunk);
        }

        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(TrunkConverter.convert(trunks)), context)
            .map(result -> new SimpleResponse<>(result, trunk));
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
     * Deletes SIP Trunk.
     *
     * @param trunk SIP Trunk.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Trunk>> deleteTrunkWithResponse(Trunk trunk, Context context) {
        List<Trunk> trunks = getTrunks().block();
        Integer deleteIndex = findIndex(trunks, trunk);

        if (deleteIndex != null) {
            Trunk removedTrunk = trunks.remove((int)deleteIndex);
            setTrunksWithResponse(trunks, context);

            return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(TrunkConverter.convert(trunks)), context)
                .map(result -> new SimpleResponse<>(result, removedTrunk));
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
     * Gets SIP Trunk Routes.
     *
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<TrunkRoute>>> getRoutesWithResponse(Context context) {
        return client.getSipConfigurationWithResponseAsync(context)
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
    public Mono<List<TrunkRoute>> setRoutes(List<TrunkRoute> routes) {
        Mono<SipConfiguration> configuration = setSipConfiguration(new SipConfiguration(routes));
        return configuration.map(SipConfiguration::getRoutes);
    }

    /**
     * Sets SIP Trunk Routes.
     *
     * @param routes SIP Trunk Routes.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Routes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<List<Trunk>>> setRoutesWithResponse(List<TrunkRoute> routes, Context context) {
        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(routes), context)
            .map(result -> new SimpleResponse<>(result, TrunkConverter.convert(result.getValue().getTrunks())));
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
     * Sets SIP Trunk Route.
     * If a route with specified name already exists, it will be replaced, otherwise a new route will be added.
     *
     * @param route SIP Trunk Route.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Route.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<TrunkRoute>> setRouteWithResponse(TrunkRoute route, Context context) {
        List<TrunkRoute> routes = getRoutes().block();
        Integer setIndex = findIndex(routes, route);
        if (setIndex != null) {
            routes.set(setIndex, route);
        } else {
            routes.add(route);
        }

        return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(routes), context)
            .map(result -> new SimpleResponse<>(result, route));
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
     * Deletes SIP Trunk Route.
     *
     * @param route SIP Trunk Route.
     * @param context the context of the request. Can also be null or Context.NONE.
     * @return Response object with the SIP Trunk Route.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<TrunkRoute>> deleteRouteWithResponse(TrunkRoute route, Context context) {
        List<TrunkRoute> routes = getRoutes().block();
        Integer deleteIndex = findIndex(routes, route);

        if (deleteIndex != null) {
            TrunkRoute removedRoute = routes.remove((int)deleteIndex);
            setRoutesWithResponse(routes, context);

            return client.patchSipConfigurationWithResponseAsync(new SipConfiguration(routes), context)
                .map(result -> new SimpleResponse<>(result, removedRoute));
        }
        return null;
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
