// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImpl;
import com.azure.communication.phonenumbers.siprouting.implementation.converters.SipRoutingErrorConverter;
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
import java.util.Map;

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
    public Map<String, Trunk> getTrunks() {
        SipConfiguration configuration = getSipConfiguration();
        return configuration.getTrunks();
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
     * Updates SIP Trunks.
     *
     * @param trunks SIP Trunks.
     * @return SIP Trunks.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Map<String, Trunk> setTrunks(Map<String, Trunk> trunks) {
        SipConfiguration configuration = setSipConfiguration(new SipConfiguration(trunks));
        return configuration.getTrunks();
    }

    /**
     * Updates SIP Trunk Routes.
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
}
