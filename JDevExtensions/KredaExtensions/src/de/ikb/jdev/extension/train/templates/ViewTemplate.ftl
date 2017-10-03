<?xml version='1.0' encoding='UTF-8'?>
<!DOCTYPE html>
<f:view xmlns:f="http://java.sun.com/jsf/core" xmlns:af="http://xmlns.oracle.com/adf/faces/rich"
        xmlns:c="http://java.sun.com/jsp/jstl/core">
    <c:set var="${trainModel.uiBundleVarName}Bundle" value="<#noparse>#{adfBundle['</#noparse>${trainModel.uiBundleName}']}"/>
    <!-- SuppressWarning: oracle.adf.faces.compreqtitle -->
    <af:document id="d1">
        <af:messages id="m1"/>
        <af:form id="f1">
            <af:pageTemplate viewId="/WEB-INF/ikb.adf.kreda.basis/templates/trainTemplate.jsf" id="pt1">
                <f:facet name="dataForm">  
                    <af:outputText value="Test" id="ot101" noWrap="true"/>
                </f:facet>
                <f:attribute name="headerHeight" value="1"/>
                <f:attribute name="styleClass" value="ikbDetail"/>
            </af:pageTemplate>
        </af:form>
    </af:document>
</f:view>