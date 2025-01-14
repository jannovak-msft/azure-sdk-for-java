// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.hdinsight.containers.models;

import com.azure.core.annotation.Immutable;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/** Properties of cluster job. */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "jobType",
    defaultImpl = ClusterJobProperties.class)
@JsonTypeName("ClusterJobProperties")
@JsonSubTypes({@JsonSubTypes.Type(name = "FlinkJob", value = FlinkJobProperties.class)})
@Immutable
public class ClusterJobProperties {
    /** Creates an instance of ClusterJobProperties class. */
    public ClusterJobProperties() {
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
