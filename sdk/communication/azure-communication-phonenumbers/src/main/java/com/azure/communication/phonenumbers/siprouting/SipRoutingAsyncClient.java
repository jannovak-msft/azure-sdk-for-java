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
import reactor.core.publisher.Mono;

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
     * Gets SIP configuration.
     *
     * @return SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SipConfiguration> getSipConfiguration() {
        return this.client.getSipConfigurationAsync()
            .onErrorMap(CommunicationErrorResponseException.class, e -> translateException(e));
    }

    /**
     * Gets SIP configuration.
     *
     * @return Response object with the SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<SipConfiguration>> getSipConfigurationWithResponse() {
        return this.client.getSipConfigurationWithResponseAsync()
            .onErrorMap(CommunicationErrorResponseException.class, e -> translateException(e));
    }

    /**
     * Updates SIP configuration.
     *
     * @param update the configuration update.
     * @return SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SipConfiguration> updateSipConfiguration(SipConfiguration update) {
        return this.client.patchSipConfigurationAsync(update)
            .onErrorMap(CommunicationErrorResponseException.class, e -> translateException(e));
    }

    /**
     * Updates SIP configuration.
     *
     * @param update the configuration update.
     * @return Response object with the SIP configuration.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<SipConfiguration>> updateSipConfigurationWithResponse(SipConfiguration update) {
        return this.client.patchSipConfigurationWithResponseAsync(update)
            .onErrorMap(CommunicationErrorResponseException.class, e -> translateException(e));
    }

    private SipRoutingResponseException translateException(CommunicationErrorResponseException exception) {
        SipRoutingError error = null;
        if (exception.getValue() != null) {
            error = SipRoutingErrorConverter.convert(exception.getValue().getError());
        }
        return new SipRoutingResponseException(exception.getMessage(), exception.getResponse(), error);
    }
}
