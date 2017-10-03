Vorlage-Button zum Kopieren

<#list trainModel.steps as step>
<af:button id="b${step?counter}" useWindow="true" 
                  windowEmbedStyle="inlineDocument" 
                  windowModalityType="applicationModal" 
                  windowHeight="1000" 
                  windowWidth="1000" 
                  shortDesc="TODO" 
                  returnListener="<#noparse>#</#noparse>{pageFlowScope.controller.onDialogReturn}"
                  icon="/adf/images/edit.png"
                  disabledIcon="/adf/images/edit_disabled.png">
                  action="TODO" 
                  disabled="TODO"                                   
                  <af:setActionListener from="${step.name}" to="<#noparse>#</#noparse>{pageFlowScope.controller.initialStep}"/>
                  <af:setActionListener from="update" to="<#noparse>#</#noparse>{pageFlowScope.controller.operation}"/>
                  <af:setActionListener from="TODO ID" to="TODO ID"/>
</af:button>



</#list> 