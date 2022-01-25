// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.common.implementation.CommunicationConnectionString;
import com.azure.communication.common.implementation.HmacAuthenticationPolicy;
import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImpl;
import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImplBuilder;
import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.util.ClientOptions;
import com.azure.core.util.Configuration;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.logging.ClientLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * SipRoutingClientBuilder that creates SipRoutingAsyncClient and SipRoutingClient.
 */
@ServiceClientBuilder(serviceClients = {
    com.azure.communication.phonenumbers.siprouting.SipRoutingClient.class,
    com.azure.communication.phonenumbers.siprouting.SipRoutingAsyncClient.class
})
public final class SipRoutingClientBuilder {
    private static final String APP_CONFIG_PROPERTIES = "azure-communication-phonenumbers-siprouting.properties";
    private static final Map<String, String> PROPERTIES = CoreUtils.getProperties(APP_CONFIG_PROPERTIES);
    private final ClientLogger logger = new ClientLogger(SipRoutingClientBuilder.class);

    private String endpoint;
    /**
     * Sets endpoint of the service
     *
     * @param endpoint url of the service
     * @return SipRoutingClientBuilder
     */
    public SipRoutingClientBuilder endpoint(String endpoint) {
        this.endpoint = Objects.requireNonNull(endpoint, "'endpoint' cannot be null.");
        return this;
    }

    private HttpPipeline pipeline;
    /**
     * Sets endpoint of the service
     *
     * @param pipeline HttpPipeline to use, if a pipeline is not
     * supplied, the credential and httpClient fields must be set
     * @return SipRoutingClientBuilder
     */
    public SipRoutingClientBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    private AzureKeyCredential credential;
    /**
     * Sets credential to use
     *
     * @param credential credential for signing, overridden by the
     * pipeline field.
     * @return SipRoutingClientBuilder
     */
    public SipRoutingClientBuilder credential(AzureKeyCredential credential) {
        this.credential = Objects.requireNonNull(credential, "'keyCredential' cannot be null.");
        return this;
    }

    private HttpClient httpClient;
    /**
     * Sets httpClient to use
     *
     * @param httpClient httpClient to use, overridden by the pipeline
     * field.
     * @return SipRoutingClientBuilder
     */
    public SipRoutingClientBuilder httpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    private HttpLogOptions httpLogOptions;
    /**
     * Sets the {@link HttpLogOptions} for service requests.
     *
     * @param logOptions The logging configuration to use when sending and receiving HTTP requests/responses.
     * @return the updated SipRoutingClientBuilder object
     */
    public SipRoutingClientBuilder httpLogOptions(HttpLogOptions logOptions) {
        this.httpLogOptions = logOptions;
        return this;
    }

    private final List<HttpPipelinePolicy> additionalPolicies = new ArrayList<>();
    /**
     * Apply additional HttpPipelinePolicy
     *
     * @param customPolicy HttpPipelinePolicy object to be applied after
     *                       AzureKeyCredentialPolicy, UserAgentPolicy, RetryPolicy, and CookiePolicy
     * @return SipRoutingClientBuilder
     */
    public SipRoutingClientBuilder addPolicy(HttpPipelinePolicy customPolicy) {
        this.additionalPolicies.add(Objects.requireNonNull(customPolicy, "'policy' cannot be null."));
        return this;
    }

    private RetryPolicy retryPolicy;
    /**
     * Sets the {@link HttpPipelinePolicy} that is used when each request is sent.
     *
     * The default retry policy will be used if not provided {@link SipRoutingClientBuilder#buildAsyncClient()}
     * to build {@link com.azure.communication.phonenumbers.siprouting.SipRoutingAsyncClient} or {@link com.azure.communication.phonenumbers.siprouting.SipRoutingClient}.
     * @param retryPolicy user's retry policy applied to each request.
     * @return The updated ConfigurationClientBuilder object.
     */
    public SipRoutingClientBuilder retryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = Objects.requireNonNull(retryPolicy, "The retry policy cannot be null");
        return this;
    }

    private Configuration configuration;
    /**
     * Sets the configuration object used to retrieve environment configuration values during building of the client.
     *
     * @param configuration Configuration store used to retrieve environment configurations.
     * @return SipRoutingClientBuilder
     */
    public SipRoutingClientBuilder configuration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    private SipRoutingServiceVersion version = SipRoutingServiceVersion.getLatest();
    /**
     * Sets the {@link SipRoutingServiceVersion} that is used when making API requests.
     * <p>
     * If a service version is not provided, the service version that will be used will be the latest known service
     * version based on the version of the client library being used. If no service version is specified, updating to a
     * newer version of the client library will have the result of potentially moving to a newer service version.
     * <p>
     * Targeting a specific service version may also mean that the service will return an error for newer APIs.
     *
     * @param serviceVersion The service API version to use.
     * @return the updated DigitalTwinsClientBuilder instance for fluent building.
     */
    public SipRoutingClientBuilder serviceVersion(SipRoutingServiceVersion serviceVersion) {
        this.version = serviceVersion;
        return this;
    }

    private ClientOptions clientOptions;
    /**
     * Sets the client options which enables various options to be set on the client.
     *
     * @param clientOptions to be set on the client.
     * @return The updated {@link SipRoutingClientBuilder} object.
     */
    public SipRoutingClientBuilder clientOptions(ClientOptions clientOptions) {
        this.clientOptions = Objects.requireNonNull(clientOptions, "'clientOptions' cannot be null.");
        return this;
    }

    /**
     * Sets the credential to use when authenticating HTTP requests. Also, sets the {@link #endpoint(String) endpoint}
     * for this SipRoutingClientBuilder.
     *
     * @param connectionString Connection string in the format "endpoint={endpoint_value};accesskey={accesskey_value}"
     * @return The updated SipRoutingClientBuilder object.
     * @throws NullPointerException If {@code connectionString} is {@code null}.
     * @throws IllegalArgumentException if {@code connectionString} is an empty string, the {@code connectionString}
     * secret is invalid, or the HMAC-SHA256 MAC algorithm cannot be instantiated.
     */
    public SipRoutingClientBuilder connectionString(String connectionString) {
        Objects.requireNonNull(connectionString, "'connectionString' cannot be null.");
        CommunicationConnectionString connectionStringObject = new CommunicationConnectionString(connectionString);
        String endpoint = connectionStringObject.getEndpoint();
        String accessKey = connectionStringObject.getAccessKey();
        this
            .endpoint(endpoint)
            .credential(new AzureKeyCredential(accessKey));
        return this;
    }

    /**
     * Create asynchronous client applying HMACAuthenticationPolicy, UserAgentPolicy,
     * RetryPolicy, and CookiePolicy.
     * Additional HttpPolicies specified by additionalPolicies will be applied after them
     *
     * @return SipRoutingAsyncClient instance
     */
    public SipRoutingAsyncClient buildAsyncClient() {
        validateRequiredFields();
        if (this.version != null) {
            logger.info("Build client for service version" + this.version.getVersion());
        }

        return new SipRoutingAsyncClient(createAdminClientImpl());
    }

    /**
     * Create synchronous client applying HmacAuthenticationPolicy, UserAgentPolicy,
     * RetryPolicy, and CookiePolicy.
     * Additional HttpPolicies specified by additionalPolicies will be applied after them
     *
     * @return SipRoutingClient instance
     */
    public SipRoutingClient buildClient() {
        validateRequiredFields();
        if (this.version != null) {
            logger.info("Build client for service version" + this.version.getVersion());
        }

        return new SipRoutingClient(createAdminClientImpl());
    }

    private SipRoutingAdminClientImpl createAdminClientImpl() {
        SipRoutingAdminClientImplBuilder builder = new SipRoutingAdminClientImplBuilder()
            .endpoint(endpoint)
            .pipeline(pipeline)
            .httpClient(httpClient)
            .httpLogOptions(httpLogOptions)
            .retryPolicy(retryPolicy)
            .configuration(configuration)
            .apiVersion(version.getVersion())
            .clientOptions(clientOptions);

        builder.addPolicy(createAuthenticationPolicy());
        additionalPolicies.forEach(builder::addPolicy);

        return builder.buildClient();
    }

    HttpPipelinePolicy createAuthenticationPolicy() {
        if (credential != null) {
            return new HmacAuthenticationPolicy(credential);
        } else {
            throw logger.logExceptionAsError(
                new NullPointerException("Missing credential information while building a client."));
        }
    }

    private void validateRequiredFields() {
        Objects.requireNonNull(endpoint);
    }
}
