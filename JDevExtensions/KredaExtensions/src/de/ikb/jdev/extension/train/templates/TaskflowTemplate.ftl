<?xml version="1.0" encoding="UTF-8" ?>
<adfc-config xmlns="http://xmlns.oracle.com/adf/controller" version="1.2">
    <task-flow-definition id="${trainModel.trainName?uncap_first}TrainTF">
        <template-reference>
            <document>/WEB-INF/ikb.adf.kreda.basis/templates/train-taskflow-template.xml</document>
            <id>train-taskflow-template</id>
        </template-reference>
        <default-activity>defaultActivity</default-activity>
        <transaction>
            <new-transaction/>
        </transaction>
        <data-control-scope>
            <isolated/>
        </data-control-scope>
        <managed-bean id="__1">
            <managed-bean-name>trainController</managed-bean-name>
            <managed-bean-class>${trainModel.packageName}.${trainModel.trainName}TrainController</managed-bean-class>
            <managed-bean-scope>pageFlow</managed-bean-scope>
        </managed-bean>
        <managed-bean id="__2">
            <managed-bean-name>messageContext</managed-bean-name>
            <managed-bean-class>${trainModel.packageName}.${trainModel.trainName}TrainMessageContext</managed-bean-class>
            <managed-bean-scope>pageFlow</managed-bean-scope>
        </managed-bean>
        <method-call id="defaultActivity">
            <method><#noparse>#{pageFlowScope.trainController.defaultActivity}</#noparse></method>
            <outcome>
                <to-string/>
            </outcome>
        </method-call>
        <method-call id="initTrainSteps">
            <method><#noparse>#{pageFlowScope.trainController.initTrainSteps}</#noparse></method>
            <outcome>
                <to-string/>
            </outcome>
        </method-call>
        <#list trainModel.steps as step>
            <view id="${step.name?uncap_first}">
                <page>${trainModel.jsfDirPath}${step.name?uncap_first}.jsf</page>
            </view>
        </#list>
        <router id="insertOrUpdate">
            <case id="__36">
                <expression id="__16"><#noparse>#</#noparse>{pageFlowScope.trainController.operation eq "insert"}</expression>
                <outcome id="__35">insert</outcome>
            </case>
            <default-outcome id="__15">update</default-outcome>
        </router>
        <method-call id="ExecuteWithParams">
            <outcome id="__18">
                <fixed-outcome id="__17">ExecuteWithParams</fixed-outcome>
            </outcome>
        </method-call>
        <method-call id="CreateInsert">
            <outcome>
                <fixed-outcome>CreateInsert</fixed-outcome>
            </outcome>
        </method-call>
        <control-flow-rule id="__9">
            <from-activity-id>defaultActivity</from-activity-id>
            <control-flow-case id="__10">
                <from-outcome>start</from-outcome>
                <to-activity-id>insertOrUpdate</to-activity-id>
            </control-flow-case>
        </control-flow-rule>
        <control-flow-rule id="__11">
            <from-activity-id>*</from-activity-id>
            <#list trainModel.steps as step>
                <control-flow-case id="__5${step?counter}">
                    <from-outcome>go${step.name}</from-outcome>
                    <to-activity-id>${step.name?uncap_first}</to-activity-id>
                </control-flow-case>
            </#list>
        </control-flow-rule>
        <control-flow-rule id="__7">
            <from-activity-id>insertOrUpdate</from-activity-id>
            <control-flow-case id="__8">
                <from-outcome>update</from-outcome>
                <to-activity-id>ExecuteWithParams</to-activity-id>
            </control-flow-case>
            <control-flow-case id="__23">
                <from-outcome>insert</from-outcome>
                <to-activity-id>CreateInsert</to-activity-id>
            </control-flow-case>
        </control-flow-rule>
        <control-flow-rule id="__24">
            <from-activity-id>CreateInsert</from-activity-id>
            <control-flow-case id="__25">
                <from-outcome>CreateInsert</from-outcome>
                <to-activity-id>initTrainSteps</to-activity-id>
            </control-flow-case>
        </control-flow-rule>
        <control-flow-rule id="__12">
            <from-activity-id>ExecuteWithParams</from-activity-id>
            <control-flow-case id="__13">
                <from-outcome>ExecuteWithParams</from-outcome>
                <to-activity-id>initTrainSteps</to-activity-id>
            </control-flow-case>
        </control-flow-rule>
    </task-flow-definition>
</adfc-config>