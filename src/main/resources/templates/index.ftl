<#-- @ftlvariable name="cycles" type="java.util.List<grove.persistence.Cycle>" -->

<#list cycles as cycle>
    <p>${cycle.name} -> ${cycle.financialYear}${cycle.quarter}</p>
</#list>