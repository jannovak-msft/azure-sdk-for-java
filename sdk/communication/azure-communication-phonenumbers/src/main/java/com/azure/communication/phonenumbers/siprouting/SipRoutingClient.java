// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImpl;
import com.azure.communication.phonenumbers.siprouting.implementation.converters.SipRoutingErrorConverter;
import com.azure.communication.phonenumbers.siprouting.implementation.models.CommunicationErrorResponseException;
import com.azure.communication.phonenumbers.siprouting.models.SipConfiguration;
import com.azure.communication.phonenumbers.siprouting.models.SipRoutingError;
import com.azure.communication.phonenumbers.siprouting.models.SipRoutingResponseException;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;

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
     * Gets SIP configuration.
     *
     * @return SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public SipConfiguration getSipConfiguration() {
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
     * Updates SIP configuration.
     *
     * @param update the configuration update.
     * @return SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public SipConfiguration setSipConfiguration(SipConfiguration update) {
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
