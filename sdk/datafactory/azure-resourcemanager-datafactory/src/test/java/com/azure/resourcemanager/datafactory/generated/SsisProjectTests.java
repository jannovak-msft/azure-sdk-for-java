// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.datafactory.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.datafactory.models.SsisEnvironmentReference;
import com.azure.resourcemanager.datafactory.models.SsisParameter;
import com.azure.resourcemanager.datafactory.models.SsisProject;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;

public final class SsisProjectTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        SsisProject model = BinaryData.fromString(
            "{\"type\":\"Project\",\"folderId\":3261509387965397992,\"version\":8732628572819143498,\"environmentRefs\":[{\"id\":4676424340890404374,\"environmentFolderName\":\"bwhxmyibxkcegct\",\"environmentName\":\"xkxtcxbbjbeyqoh\",\"referenceType\":\"awpjfkrarerdlg\"}],\"parameters\":[{\"id\":7203314562819543762,\"name\":\"gowwdocjasuamegj\",\"description\":\"iszhexu\",\"dataType\":\"avwesloblit\",\"required\":false,\"sensitive\":true,\"designDefaultValue\":\"c\",\"defaultValue\":\"anjsoutrzto\",\"sensitiveDefaultValue\":\"jgb\",\"valueType\":\"lxhwkzfggsuzk\",\"valueSet\":true,\"variable\":\"tpzu\"},{\"id\":1360986389377399064,\"name\":\"abth\",\"description\":\"ctcabcpwab\",\"dataType\":\"ihszfk\",\"required\":false,\"sensitive\":true,\"designDefaultValue\":\"wegvuojuwgweccvu\",\"defaultValue\":\"qvfcfsssmyaemk\",\"sensitiveDefaultValue\":\"bsdgktluifiqgp\",\"valueType\":\"penobqysbeespq\",\"valueSet\":false,\"variable\":\"erszsu\"},{\"id\":7841327281866489728,\"name\":\"tbricvvofenint\",\"description\":\"nhyyqxckd\",\"dataType\":\"jpisrdnow\",\"required\":true,\"sensitive\":false,\"designDefaultValue\":\"fvijnu\",\"defaultValue\":\"fiiytqxewjsyu\",\"sensitiveDefaultValue\":\"zlghkvoxdp\",\"valueType\":\"xkivbkuto\",\"valueSet\":false,\"variable\":\"qoytwssbvqnpwdw\"},{\"id\":1134321929087748852,\"name\":\"a\",\"description\":\"bqpwx\",\"dataType\":\"lzrmiukoth\",\"required\":false,\"sensitive\":true,\"designDefaultValue\":\"hdhfrvsizfwgn\",\"defaultValue\":\"jniaffwcgj\",\"sensitiveDefaultValue\":\"o\",\"valueType\":\"uuogdkpnm\",\"valueSet\":false,\"variable\":\"qjdebyxqucnbgi\"}],\"id\":205840051986043435,\"name\":\"wdkouzyvi\",\"description\":\"v\"}")
            .toObject(SsisProject.class);
        Assertions.assertEquals(205840051986043435L, model.id());
        Assertions.assertEquals("wdkouzyvi", model.name());
        Assertions.assertEquals("v", model.description());
        Assertions.assertEquals(3261509387965397992L, model.folderId());
        Assertions.assertEquals(8732628572819143498L, model.version());
        Assertions.assertEquals(4676424340890404374L, model.environmentRefs().get(0).id());
        Assertions.assertEquals("bwhxmyibxkcegct", model.environmentRefs().get(0).environmentFolderName());
        Assertions.assertEquals("xkxtcxbbjbeyqoh", model.environmentRefs().get(0).environmentName());
        Assertions.assertEquals("awpjfkrarerdlg", model.environmentRefs().get(0).referenceType());
        Assertions.assertEquals(7203314562819543762L, model.parameters().get(0).id());
        Assertions.assertEquals("gowwdocjasuamegj", model.parameters().get(0).name());
        Assertions.assertEquals("iszhexu", model.parameters().get(0).description());
        Assertions.assertEquals("avwesloblit", model.parameters().get(0).dataType());
        Assertions.assertEquals(false, model.parameters().get(0).required());
        Assertions.assertEquals(true, model.parameters().get(0).sensitive());
        Assertions.assertEquals("c", model.parameters().get(0).designDefaultValue());
        Assertions.assertEquals("anjsoutrzto", model.parameters().get(0).defaultValue());
        Assertions.assertEquals("jgb", model.parameters().get(0).sensitiveDefaultValue());
        Assertions.assertEquals("lxhwkzfggsuzk", model.parameters().get(0).valueType());
        Assertions.assertEquals(true, model.parameters().get(0).valueSet());
        Assertions.assertEquals("tpzu", model.parameters().get(0).variable());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        SsisProject model
            = new SsisProject().withId(205840051986043435L).withName("wdkouzyvi").withDescription("v")
                .withFolderId(3261509387965397992L).withVersion(8732628572819143498L)
                .withEnvironmentRefs(Arrays.asList(new SsisEnvironmentReference().withId(4676424340890404374L)
                    .withEnvironmentFolderName("bwhxmyibxkcegct").withEnvironmentName("xkxtcxbbjbeyqoh")
                    .withReferenceType("awpjfkrarerdlg")))
                .withParameters(Arrays.asList(
                    new SsisParameter().withId(7203314562819543762L).withName("gowwdocjasuamegj")
                        .withDescription("iszhexu").withDataType("avwesloblit").withRequired(false).withSensitive(true)
                        .withDesignDefaultValue("c").withDefaultValue("anjsoutrzto").withSensitiveDefaultValue("jgb")
                        .withValueType("lxhwkzfggsuzk").withValueSet(true).withVariable("tpzu"),
                    new SsisParameter().withId(1360986389377399064L).withName("abth").withDescription("ctcabcpwab")
                        .withDataType("ihszfk").withRequired(false).withSensitive(true)
                        .withDesignDefaultValue("wegvuojuwgweccvu").withDefaultValue("qvfcfsssmyaemk")
                        .withSensitiveDefaultValue("bsdgktluifiqgp").withValueType("penobqysbeespq").withValueSet(false)
                        .withVariable("erszsu"),
                    new SsisParameter().withId(7841327281866489728L).withName("tbricvvofenint")
                        .withDescription("nhyyqxckd").withDataType("jpisrdnow").withRequired(true).withSensitive(false)
                        .withDesignDefaultValue("fvijnu").withDefaultValue("fiiytqxewjsyu")
                        .withSensitiveDefaultValue("zlghkvoxdp").withValueType("xkivbkuto").withValueSet(false)
                        .withVariable("qoytwssbvqnpwdw"),
                    new SsisParameter().withId(1134321929087748852L).withName("a").withDescription("bqpwx")
                        .withDataType("lzrmiukoth").withRequired(false).withSensitive(true)
                        .withDesignDefaultValue("hdhfrvsizfwgn").withDefaultValue("jniaffwcgj")
                        .withSensitiveDefaultValue("o").withValueType("uuogdkpnm").withValueSet(false)
                        .withVariable("qjdebyxqucnbgi")));
        model = BinaryData.fromObject(model).toObject(SsisProject.class);
        Assertions.assertEquals(205840051986043435L, model.id());
        Assertions.assertEquals("wdkouzyvi", model.name());
        Assertions.assertEquals("v", model.description());
        Assertions.assertEquals(3261509387965397992L, model.folderId());
        Assertions.assertEquals(8732628572819143498L, model.version());
        Assertions.assertEquals(4676424340890404374L, model.environmentRefs().get(0).id());
        Assertions.assertEquals("bwhxmyibxkcegct", model.environmentRefs().get(0).environmentFolderName());
        Assertions.assertEquals("xkxtcxbbjbeyqoh", model.environmentRefs().get(0).environmentName());
        Assertions.assertEquals("awpjfkrarerdlg", model.environmentRefs().get(0).referenceType());
        Assertions.assertEquals(7203314562819543762L, model.parameters().get(0).id());
        Assertions.assertEquals("gowwdocjasuamegj", model.parameters().get(0).name());
        Assertions.assertEquals("iszhexu", model.parameters().get(0).description());
        Assertions.assertEquals("avwesloblit", model.parameters().get(0).dataType());
        Assertions.assertEquals(false, model.parameters().get(0).required());
        Assertions.assertEquals(true, model.parameters().get(0).sensitive());
        Assertions.assertEquals("c", model.parameters().get(0).designDefaultValue());
        Assertions.assertEquals("anjsoutrzto", model.parameters().get(0).defaultValue());
        Assertions.assertEquals("jgb", model.parameters().get(0).sensitiveDefaultValue());
        Assertions.assertEquals("lxhwkzfggsuzk", model.parameters().get(0).valueType());
        Assertions.assertEquals(true, model.parameters().get(0).valueSet());
        Assertions.assertEquals("tpzu", model.parameters().get(0).variable());
    }
}
