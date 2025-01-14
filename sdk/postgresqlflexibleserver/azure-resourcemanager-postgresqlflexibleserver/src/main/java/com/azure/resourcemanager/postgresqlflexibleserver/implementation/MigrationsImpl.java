// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.postgresqlflexibleserver.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.postgresqlflexibleserver.fluent.MigrationsClient;
import com.azure.resourcemanager.postgresqlflexibleserver.fluent.models.MigrationResourceInner;
import com.azure.resourcemanager.postgresqlflexibleserver.models.MigrationListFilter;
import com.azure.resourcemanager.postgresqlflexibleserver.models.MigrationResource;
import com.azure.resourcemanager.postgresqlflexibleserver.models.Migrations;

public final class MigrationsImpl implements Migrations {
    private static final ClientLogger LOGGER = new ClientLogger(MigrationsImpl.class);

    private final MigrationsClient innerClient;

    private final com.azure.resourcemanager.postgresqlflexibleserver.PostgreSqlManager serviceManager;

    public MigrationsImpl(
        MigrationsClient innerClient,
        com.azure.resourcemanager.postgresqlflexibleserver.PostgreSqlManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public Response<MigrationResource> getWithResponse(
        String subscriptionId,
        String resourceGroupName,
        String targetDbServerName,
        String migrationName,
        Context context) {
        Response<MigrationResourceInner> inner =
            this
                .serviceClient()
                .getWithResponse(subscriptionId, resourceGroupName, targetDbServerName, migrationName, context);
        if (inner != null) {
            return new SimpleResponse<>(
                inner.getRequest(),
                inner.getStatusCode(),
                inner.getHeaders(),
                new MigrationResourceImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public MigrationResource get(
        String subscriptionId, String resourceGroupName, String targetDbServerName, String migrationName) {
        MigrationResourceInner inner =
            this.serviceClient().get(subscriptionId, resourceGroupName, targetDbServerName, migrationName);
        if (inner != null) {
            return new MigrationResourceImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Response<Void> deleteWithResponse(
        String subscriptionId,
        String resourceGroupName,
        String targetDbServerName,
        String migrationName,
        Context context) {
        return this
            .serviceClient()
            .deleteWithResponse(subscriptionId, resourceGroupName, targetDbServerName, migrationName, context);
    }

    public void delete(
        String subscriptionId, String resourceGroupName, String targetDbServerName, String migrationName) {
        this.serviceClient().delete(subscriptionId, resourceGroupName, targetDbServerName, migrationName);
    }

    public PagedIterable<MigrationResource> listByTargetServer(
        String subscriptionId, String resourceGroupName, String targetDbServerName) {
        PagedIterable<MigrationResourceInner> inner =
            this.serviceClient().listByTargetServer(subscriptionId, resourceGroupName, targetDbServerName);
        return Utils.mapPage(inner, inner1 -> new MigrationResourceImpl(inner1, this.manager()));
    }

    public PagedIterable<MigrationResource> listByTargetServer(
        String subscriptionId,
        String resourceGroupName,
        String targetDbServerName,
        MigrationListFilter migrationListFilter,
        Context context) {
        PagedIterable<MigrationResourceInner> inner =
            this
                .serviceClient()
                .listByTargetServer(
                    subscriptionId, resourceGroupName, targetDbServerName, migrationListFilter, context);
        return Utils.mapPage(inner, inner1 -> new MigrationResourceImpl(inner1, this.manager()));
    }

    public MigrationResource getById(String id) {
        String subscriptionId = Utils.getValueFromIdByName(id, "subscriptions");
        if (subscriptionId == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'subscriptions'.", id)));
        }
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String targetDbServerName = Utils.getValueFromIdByName(id, "flexibleServers");
        if (targetDbServerName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'flexibleServers'.", id)));
        }
        String migrationName = Utils.getValueFromIdByName(id, "migrations");
        if (migrationName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'migrations'.", id)));
        }
        return this
            .getWithResponse(subscriptionId, resourceGroupName, targetDbServerName, migrationName, Context.NONE)
            .getValue();
    }

    public Response<MigrationResource> getByIdWithResponse(String id, Context context) {
        String subscriptionId = Utils.getValueFromIdByName(id, "subscriptions");
        if (subscriptionId == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'subscriptions'.", id)));
        }
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String targetDbServerName = Utils.getValueFromIdByName(id, "flexibleServers");
        if (targetDbServerName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'flexibleServers'.", id)));
        }
        String migrationName = Utils.getValueFromIdByName(id, "migrations");
        if (migrationName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'migrations'.", id)));
        }
        return this.getWithResponse(subscriptionId, resourceGroupName, targetDbServerName, migrationName, context);
    }

    public void deleteById(String id) {
        String subscriptionId = Utils.getValueFromIdByName(id, "subscriptions");
        if (subscriptionId == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'subscriptions'.", id)));
        }
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String targetDbServerName = Utils.getValueFromIdByName(id, "flexibleServers");
        if (targetDbServerName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'flexibleServers'.", id)));
        }
        String migrationName = Utils.getValueFromIdByName(id, "migrations");
        if (migrationName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'migrations'.", id)));
        }
        this.deleteWithResponse(subscriptionId, resourceGroupName, targetDbServerName, migrationName, Context.NONE);
    }

    public Response<Void> deleteByIdWithResponse(String id, Context context) {
        String subscriptionId = Utils.getValueFromIdByName(id, "subscriptions");
        if (subscriptionId == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'subscriptions'.", id)));
        }
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String targetDbServerName = Utils.getValueFromIdByName(id, "flexibleServers");
        if (targetDbServerName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'flexibleServers'.", id)));
        }
        String migrationName = Utils.getValueFromIdByName(id, "migrations");
        if (migrationName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'migrations'.", id)));
        }
        return this.deleteWithResponse(subscriptionId, resourceGroupName, targetDbServerName, migrationName, context);
    }

    private MigrationsClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.resourcemanager.postgresqlflexibleserver.PostgreSqlManager manager() {
        return this.serviceManager;
    }

    public MigrationResourceImpl define(String name) {
        return new MigrationResourceImpl(name, this.manager());
    }
}
