<?xml version="1.0" encoding="UTF-8" ?>
<adfc-config xmlns="http://xmlns.oracle.com/adf/controller" version="1.2" id="__22">
  <task-flow-definition id="${taskflowModel.taskflowName}TF">
    <template-reference>
      <document id="__2">/WEB-INF/ikb.adf.kreda.basis/templates/kreda-basis-template.xml</document>
      <id id="__1">kreda-basis-template</id>
    </template-reference>
    <default-activity id="__28">defaultActivity</default-activity>
    <transaction>
      <requires-transaction/>
    </transaction>
    <data-control-scope>
      <shared/>
    </data-control-scope>   
    <managed-bean id="__4">
      <managed-bean-name id="__3">controller</managed-bean-name>
      <managed-bean-class id="__6">${taskflowModel.packageName}.${taskflowModel.taskflowName?cap_first}Controller</managed-bean-class>
      <managed-bean-scope id="__5">pageFlow</managed-bean-scope>
    </managed-bean>  
    <method-call id="defaultActivity">
      <method id="__43"><#noparse>#</#noparse>{pageFlowScope.controller.defaultActivity}</method>
      <outcome id="__29">
        <to-string/>
      </outcome>
    </method-call>   
    <view id="${taskflowModel.taskflowName?uncap_first}">
      <page id="__44">${taskflowModel.jsfDirPath}${taskflowModel.taskflowName?uncap_first}.jsff</page>
    </view>
    <control-flow-rule id="__30">
      <from-activity-id id="__31">defaultActivity</from-activity-id>
      <control-flow-case id="__33">
        <from-outcome id="__39">start</from-outcome>
        <to-activity-id id="__40">${taskflowModel.taskflowName?uncap_first}</to-activity-id>
      </control-flow-case>
    </control-flow-rule>   
    <use-page-fragments/>
  </task-flow-definition>
</adfc-config>
