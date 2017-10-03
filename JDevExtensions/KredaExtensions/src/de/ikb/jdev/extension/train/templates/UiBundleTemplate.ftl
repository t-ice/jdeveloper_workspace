
<#list trainModel.steps as step>
${trainModel.trainName?upper_case}TRAIN_STEP_${step.name?upper_case}=${step.escapedName}
</#list>