// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting;

import com.azure.communication.common.implementation.CommunicationConnectionString;
import com.azure.communication.common.implementation.HmacAuthenticationPolicy;
import com.azure.communication.phonenumbers.PhoneNumbersClientBuilder;
import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImpl;
import com.azure.communication.phonenumbers.siprouting.implementation.SipRoutingAdminClientImplBuilder;
import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.BearerTokenAuthenticationPolicy;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.policy.RequestIdPolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
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
@ServiceClientBuilder(serviceClients = {SipRoutingClient.class, SipRoutingAsyncClient.class})
public final class SipRoutingClientBuilder {
    private static final String APP_CONFIG_PROPERTIES = "azure-communication-phonenumbers-siprouting.properties";
    private static final Map<String, String> PROPERTIES = CoreUtils.getProperties(APP_CONFIG_PROPERTIES);
    private static final String SDK_NAME = "name";
    private static final String SDK_VERSION = "version";

    private final ClientLogger logger = new ClientLogger(SipRoutingClientBuilder.class);

    private SipRoutingServiceVersion version = SipRoutingServiceVersion.getLatest();
    private String endpoint;
    private HttpPipeline pipeline;
    private HttpClient httpClient;
    private HttpLogOptions httpLogOptions;
    private AzureKeyCredential azureKeyCredential;
    private TokenCredential tokenCredential;
    private Configuration configuration;
    private ClientOptions clientOptions;
    private RetryPolicy retryPolicy;
    private final List<HttpPipelinePolicy> additionalPolicies = new ArrayList<>();

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

    /**
     * Sets the {@link AzureKeyCredential} used to authenticate HTTP requests.
     *
     * @param keyCredential The {@link AzureKeyCredential} used to authenticate HTTP requests.
     * @return The updated {@link PhoneNumbersClientBuilder} object.
     * @throws NullPointerException If {@code keyCredential} is null.
     */
    public SipRoutingClientBuilder credential(AzureKeyCredential keyCredential) {
        this.azureKeyCredential = Objects.requireNonNull(keyCredential, "'keyCredential' cannot be null.");
        return this;
    }

    /**
     * Sets the {@link TokenCredential} used to authenticate HTTP requests.
     *
     * @param tokenCredential {@link TokenCredential} used to authenticate HTTP requests.
     * @return The updated {@link SipRoutingClientBuilder} object.
     * @throws NullPointerException If {@code tokenCredential} is null.
     */
    public SipRoutingClientBuilder credential(TokenCredential tokenCredential) {
        this.tokenCredential = Objects.requireNonNull(tokenCredential, "'tokenCredential' cannot be null.");
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
     * Sets the configuration object used to retrieve environment configuration values during building of the client.
     *
     * @param configuration Configuration store used to retrieve environment configurations.
     * @return SipRoutingClientBuilder
     */
    public SipRoutingClientBuilder configuration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }
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

    /**
     * Sets the {@link HttpPipelinePolicy} that is used when each request is sent.
     *
     * The default retry policy will be used if not provided {@link SipRoutingClientBuilder#buildAsyncClient()}
     * to build {@link SipRoutingAsyncClient} or {@link SipRoutingClient}.
     * @param retryPolicy user's retry policy applied to each request.
     * @return The updated ConfigurationClientBuilder object.
     */
    public SipRoutingClientBuilder retryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = Objects.requireNonNull(retryPolicy, "The retry policy cannot be null");
        return this;
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

        return createClientImpl(createAdminClientImpl());
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

        return createAsyncClientImpl(createAdminClientImpl());
    }

    SipRoutingClient createClientImpl(SipRoutingAdminClientImpl adminClientImpl) {
        return new SipRoutingClient(adminClientImpl);
    }

    SipRoutingAsyncClient createAsyncClientImpl(SipRoutingAdminClientImpl adminClientImpl) {
        return new SipRoutingAsyncClient(adminClientImpl);
    }

    private SipRoutingAdminClientImpl createAdminClientImpl() {
        return new SipRoutingAdminClientImplBuilder()
            .endpoint(this.endpoint)
            .pipeline(this.createHttpPipeline())
            .buildClient();
    }

    UserAgentPolicy createUserAgentPolicy(
        String applicationId, String sdkName, String sdkVersion, Configuration configuration) {
        return new UserAgentPolicy(applicationId, sdkName, sdkVersion, configuration);
    }

    HttpPipelinePolicy createRequestIdPolicy() {
        return new RequestIdPolicy();
    }

    CookiePolicy createCookiePolicy() {
        return new CookiePolicy();
    }

    HttpLoggingPolicy createHttpLoggingPolicy(HttpLogOptions httpLogOptions) {
        return new HttpLoggingPolicy(httpLogOptions);
    }

    HttpLogOptions createDefaultHttpLogOptions() {
        return new HttpLogOptions();
    }

    HttpPipelinePolicy createAuthenticationPolicy() {
        if (this.tokenCredential != null && this.azureKeyCredential != null) {
            throw logger.logExceptionAsError(
                new IllegalArgumentException("Both 'credential' and 'keyCredential' are set. Just one may be used."));
        }
        if (this.tokenCredential != null) {
            return new BearerTokenAuthenticationPolicy(
                this.tokenCredential, "https://communication.azure.com//.default");
        } else if (this.azureKeyCredential != null) {
            return new HmacAuthenticationPolicy(this.azureKeyCredential);
        } else {
            throw logger.logExceptionAsError(
                new NullPointerException("Missing credential information while building a client."));
        }
    }

    private HttpLogOptions getHttpLogOptions() {
        if (this.httpLogOptions == null) {
            this.httpLogOptions = this.createDefaultHttpLogOptions();
        }

        return this.httpLogOptions;
    }

    private HttpPipeline createHttpPipeline() {
        if (this.pipeline != null) {
            return this.pipeline;
        }

        List<HttpPipelinePolicy> policyList = new ArrayList<>();

        ClientOptions buildClientOptions = (clientOptions == null) ? new ClientOptions() : clientOptions;
        HttpLogOptions buildLogOptions = (httpLogOptions == null) ? new HttpLogOptions() : httpLogOptions;

        String applicationId = null;
        if (!CoreUtils.isNullOrEmpty(buildClientOptions.getApplicationId())) {
            applicationId = buildClientOptions.getApplicationId();
        } else if (!CoreUtils.isNullOrEmpty(buildLogOptions.getApplicationId())) {
            applicationId = buildLogOptions.getApplicationId();
        }

        // Add required policies
        policyList.add(this.createUserAgentPolicy(
            applicationId,
            PROPERTIES.get(SDK_NAME),
            PROPERTIES.get(SDK_VERSION),
            this.configuration
        ));
        policyList.add(this.createRequestIdPolicy());
        policyList.add(this.retryPolicy == null ? new RetryPolicy() : this.retryPolicy);
        // auth policy is per request, should be after retry
        policyList.add(this.createAuthenticationPolicy());
        policyList.add(this.createCookiePolicy());

        // Add additional policies
        if (this.additionalPolicies.size() > 0) {
            policyList.addAll(this.additionalPolicies);
        }

        // Add logging policy
        policyList.add(this.createHttpLoggingPolicy(this.getHttpLogOptions()));

        return new HttpPipelineBuilder()
            .policies(policyList.toArray(new HttpPipelinePolicy[0]))
            .httpClient(this.httpClient)
            .clientOptions(clientOptions)
            .build();
    }

    private void validateRequiredFields() {
        Objects.requireNonNull(endpoint);
    }
}
