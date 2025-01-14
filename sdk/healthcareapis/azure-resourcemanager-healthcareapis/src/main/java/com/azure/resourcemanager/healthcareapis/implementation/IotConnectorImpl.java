// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.healthcareapis.implementation;

import com.azure.core.management.Region;
import com.azure.core.management.SystemData;
import com.azure.core.util.Context;
import com.azure.resourcemanager.healthcareapis.fluent.models.IotConnectorInner;
import com.azure.resourcemanager.healthcareapis.models.IotConnector;
import com.azure.resourcemanager.healthcareapis.models.IotConnectorPatchResource;
import com.azure.resourcemanager.healthcareapis.models.IotEventHubIngestionEndpointConfiguration;
import com.azure.resourcemanager.healthcareapis.models.IotMappingProperties;
import com.azure.resourcemanager.healthcareapis.models.ProvisioningState;
import com.azure.resourcemanager.healthcareapis.models.ServiceManagedIdentityIdentity;
import java.util.Collections;
import java.util.Map;

public final class IotConnectorImpl implements IotConnector, IotConnector.Definition, IotConnector.Update {
    private IotConnectorInner innerObject;

    private final com.azure.resourcemanager.healthcareapis.HealthcareApisManager serviceManager;

    public String id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public String type() {
        return this.innerModel().type();
    }

    public String etag() {
        return this.innerModel().etag();
    }

    public String location() {
        return this.innerModel().location();
    }

    public Map<String, String> tags() {
        Map<String, String> inner = this.innerModel().tags();
        if (inner != null) {
            return Collections.unmodifiableMap(inner);
        } else {
            return Collections.emptyMap();
        }
    }

    public SystemData systemData() {
        return this.innerModel().systemData();
    }

    public ServiceManagedIdentityIdentity identity() {
        return this.innerModel().identity();
    }

    public ProvisioningState provisioningState() {
        return this.innerModel().provisioningState();
    }

    public IotEventHubIngestionEndpointConfiguration ingestionEndpointConfiguration() {
        return this.innerModel().ingestionEndpointConfiguration();
    }

    public IotMappingProperties deviceMapping() {
        return this.innerModel().deviceMapping();
    }

    public Region region() {
        return Region.fromName(this.regionName());
    }

    public String regionName() {
        return this.location();
    }

    public String resourceGroupName() {
        return resourceGroupName;
    }

    public IotConnectorInner innerModel() {
        return this.innerObject;
    }

    private com.azure.resourcemanager.healthcareapis.HealthcareApisManager manager() {
        return this.serviceManager;
    }

    private String resourceGroupName;

    private String workspaceName;

    private String iotConnectorName;

    private IotConnectorPatchResource updateIotConnectorPatchResource;

    public IotConnectorImpl withExistingWorkspace(String resourceGroupName, String workspaceName) {
        this.resourceGroupName = resourceGroupName;
        this.workspaceName = workspaceName;
        return this;
    }

    public IotConnector create() {
        this.innerObject = serviceManager.serviceClient().getIotConnectors().createOrUpdate(resourceGroupName,
            workspaceName, iotConnectorName, this.innerModel(), Context.NONE);
        return this;
    }

    public IotConnector create(Context context) {
        this.innerObject = serviceManager.serviceClient().getIotConnectors().createOrUpdate(resourceGroupName,
            workspaceName, iotConnectorName, this.innerModel(), context);
        return this;
    }

    IotConnectorImpl(String name, com.azure.resourcemanager.healthcareapis.HealthcareApisManager serviceManager) {
        this.innerObject = new IotConnectorInner();
        this.serviceManager = serviceManager;
        this.iotConnectorName = name;
    }

    public IotConnectorImpl update() {
        this.updateIotConnectorPatchResource = new IotConnectorPatchResource();
        return this;
    }

    public IotConnector apply() {
        this.innerObject = serviceManager.serviceClient().getIotConnectors().update(resourceGroupName, iotConnectorName,
            workspaceName, updateIotConnectorPatchResource, Context.NONE);
        return this;
    }

    public IotConnector apply(Context context) {
        this.innerObject = serviceManager.serviceClient().getIotConnectors().update(resourceGroupName, iotConnectorName,
            workspaceName, updateIotConnectorPatchResource, context);
        return this;
    }

    IotConnectorImpl(IotConnectorInner innerObject,
        com.azure.resourcemanager.healthcareapis.HealthcareApisManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
        this.resourceGroupName = Utils.getValueFromIdByName(innerObject.id(), "resourceGroups");
        this.iotConnectorName = Utils.getValueFromIdByName(innerObject.id(), "iotconnectors");
        this.workspaceName = Utils.getValueFromIdByName(innerObject.id(), "workspaces");
    }

    public IotConnector refresh() {
        this.innerObject = serviceManager.serviceClient().getIotConnectors()
            .getWithResponse(resourceGroupName, workspaceName, iotConnectorName, Context.NONE).getValue();
        return this;
    }

    public IotConnector refresh(Context context) {
        this.innerObject = serviceManager.serviceClient().getIotConnectors()
            .getWithResponse(resourceGroupName, workspaceName, iotConnectorName, context).getValue();
        return this;
    }

    public IotConnectorImpl withRegion(Region location) {
        this.innerModel().withLocation(location.toString());
        return this;
    }

    public IotConnectorImpl withRegion(String location) {
        this.innerModel().withLocation(location);
        return this;
    }

    public IotConnectorImpl withTags(Map<String, String> tags) {
        if (isInCreateMode()) {
            this.innerModel().withTags(tags);
            return this;
        } else {
            this.updateIotConnectorPatchResource.withTags(tags);
            return this;
        }
    }

    public IotConnectorImpl withEtag(String etag) {
        this.innerModel().withEtag(etag);
        return this;
    }

    public IotConnectorImpl withIdentity(ServiceManagedIdentityIdentity identity) {
        if (isInCreateMode()) {
            this.innerModel().withIdentity(identity);
            return this;
        } else {
            this.updateIotConnectorPatchResource.withIdentity(identity);
            return this;
        }
    }

    public IotConnectorImpl
        withIngestionEndpointConfiguration(IotEventHubIngestionEndpointConfiguration ingestionEndpointConfiguration) {
        this.innerModel().withIngestionEndpointConfiguration(ingestionEndpointConfiguration);
        return this;
    }

    public IotConnectorImpl withDeviceMapping(IotMappingProperties deviceMapping) {
        this.innerModel().withDeviceMapping(deviceMapping);
        return this;
    }

    private boolean isInCreateMode() {
        return this.innerModel().id() == null;
    }
}
