// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicesbackup.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.recoveryservicesbackup.models.AzureWorkloadRestoreRequest;
import com.azure.resourcemanager.recoveryservicesbackup.models.OverwriteOptions;
import com.azure.resourcemanager.recoveryservicesbackup.models.RecoveryMode;
import com.azure.resourcemanager.recoveryservicesbackup.models.RecoveryType;
import com.azure.resourcemanager.recoveryservicesbackup.models.TargetRestoreInfo;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public final class AzureWorkloadRestoreRequestTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        AzureWorkloadRestoreRequest model =
            BinaryData
                .fromString(
                    "{\"objectType\":\"AzureWorkloadRestoreRequest\",\"recoveryType\":\"AlternateLocation\",\"sourceResourceId\":\"sx\",\"propertyBag\":{\"sbhud\":\"fbuzjyihs\",\"foobrlttyms\":\"pohyuemslynsqyr\",\"nfwqzdzgtilaxhn\":\"nygq\",\"wivkxo\":\"hqlyvijo\"},\"targetInfo\":{\"overwriteOption\":\"Overwrite\",\"containerId\":\"ixxrtikvcpw\",\"databaseName\":\"clrcivtsox\",\"targetDirectoryForFileRestore\":\"kenx\"},\"recoveryMode\":\"WorkloadRecovery\",\"targetVirtualMachineId\":\"efrp\"}")
                .toObject(AzureWorkloadRestoreRequest.class);
        Assertions.assertEquals(RecoveryType.ALTERNATE_LOCATION, model.recoveryType());
        Assertions.assertEquals("sx", model.sourceResourceId());
        Assertions.assertEquals("fbuzjyihs", model.propertyBag().get("sbhud"));
        Assertions.assertEquals(OverwriteOptions.OVERWRITE, model.targetInfo().overwriteOption());
        Assertions.assertEquals("ixxrtikvcpw", model.targetInfo().containerId());
        Assertions.assertEquals("clrcivtsox", model.targetInfo().databaseName());
        Assertions.assertEquals("kenx", model.targetInfo().targetDirectoryForFileRestore());
        Assertions.assertEquals(RecoveryMode.WORKLOAD_RECOVERY, model.recoveryMode());
        Assertions.assertEquals("efrp", model.targetVirtualMachineId());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        AzureWorkloadRestoreRequest model =
            new AzureWorkloadRestoreRequest()
                .withRecoveryType(RecoveryType.ALTERNATE_LOCATION)
                .withSourceResourceId("sx")
                .withPropertyBag(
                    mapOf(
                        "sbhud",
                        "fbuzjyihs",
                        "foobrlttyms",
                        "pohyuemslynsqyr",
                        "nfwqzdzgtilaxhn",
                        "nygq",
                        "wivkxo",
                        "hqlyvijo"))
                .withTargetInfo(
                    new TargetRestoreInfo()
                        .withOverwriteOption(OverwriteOptions.OVERWRITE)
                        .withContainerId("ixxrtikvcpw")
                        .withDatabaseName("clrcivtsox")
                        .withTargetDirectoryForFileRestore("kenx"))
                .withRecoveryMode(RecoveryMode.WORKLOAD_RECOVERY)
                .withTargetVirtualMachineId("efrp");
        model = BinaryData.fromObject(model).toObject(AzureWorkloadRestoreRequest.class);
        Assertions.assertEquals(RecoveryType.ALTERNATE_LOCATION, model.recoveryType());
        Assertions.assertEquals("sx", model.sourceResourceId());
        Assertions.assertEquals("fbuzjyihs", model.propertyBag().get("sbhud"));
        Assertions.assertEquals(OverwriteOptions.OVERWRITE, model.targetInfo().overwriteOption());
        Assertions.assertEquals("ixxrtikvcpw", model.targetInfo().containerId());
        Assertions.assertEquals("clrcivtsox", model.targetInfo().databaseName());
        Assertions.assertEquals("kenx", model.targetInfo().targetDirectoryForFileRestore());
        Assertions.assertEquals(RecoveryMode.WORKLOAD_RECOVERY, model.recoveryMode());
        Assertions.assertEquals("efrp", model.targetVirtualMachineId());
    }

    // Use "Map.of" if available
    @SuppressWarnings("unchecked")
    private static <T> Map<String, T> mapOf(Object... inputs) {
        Map<String, T> map = new HashMap<>();
        for (int i = 0; i < inputs.length; i += 2) {
            String key = (String) inputs[i];
            T value = (T) inputs[i + 1];
            map.put(key, value);
        }
        return map;
    }
}
