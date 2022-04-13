// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.phonenumbers.siprouting.implementation.converters;

import com.azure.communication.phonenumbers.siprouting.models.Trunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A converter between {@link com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk} and
 * {@link Trunk}.
 */
public final class TrunkConverter {
    /**
     * Maps from {@link Map} to {@link List}.
     */
    public static List<Trunk> convert(Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk> obj) {
        if (obj == null) {
            return null;
        }

        List<Trunk> list = new ArrayList<>();
        for (Map.Entry<String, com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk> entry : obj.entrySet()) {
            list.add(convert(entry.getValue(), entry.getKey()));
        }
        return list;
    }

    /**
     * Maps from {@link com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk} to {@link Trunk}.
     */
    public static Trunk convert(com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk obj,
                                String fqdn) {
        if (obj == null) {
            return null;
        }

        return new Trunk().setFqdn(fqdn).setSipSignalingPort(obj.getSipSignalingPort());
    }

    /**
     * Maps from {@link List} to {@link Map}.
     */
    public static Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk> convert(List<Trunk> obj) {
        if (obj == null) {
            return null;
        }

        Map<String, com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk> map = new HashMap<>();
        for (Trunk trunk : obj) {
            map.put(trunk.getFqdn(), convert(trunk));
        }
        return map;
    }

    /**
     * Maps from {@link Trunk} to {@link com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk}.
     */
    public static com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk convert(Trunk obj) {
        if (obj == null) {
            return null;
        }

        return new com.azure.communication.phonenumbers.siprouting.implementation.models.Trunk()
            .setSipSignalingPort(obj.getSipSignalingPort());
    }

    private TrunkConverter() {
    }
}
