<#--  ACUMOS Apache-2.0 -->
 <#--Software in this Acumos repository is distributed by the authors -->
 <#--under the Apache License, Version 2.0 (the "License"). -->
 
<#--This Acumos software repository includes files that are Copyright -->
<#--2014-2017 Hippo B.V. (http://www.onehippo.com) and distributed under the -->
<#--Apache License, Version 2.0 (the "License"). -->
 
<#--You may not use this file except in compliance with the License. -->
<#--You may obtain a copy of the License at -->
 
    <#-- http://www.apache.org/licenses/LICENSE-2.0 -->
 
<#--This software is distributed on an "AS IS" BASIS, -->
<#--WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. -->
<#--See the License for the specific language governing permissions and -->
<#--limitations under the License.-->
 
<#--ACUMOS CC-BY-4.0 -->
<#--Documentation in this repository is distributed by the authors -->
<#--under the Creative Commons Attribution 4.0 International License (the "License"); -->
<#--you may not use this file except in compliance with the License. -->
<#--You may obtain a copy of the License at -->
 
<#--http://creativecommons.org/licenses/by/4.0 -->
 
<#--This documentation is distributed on an "AS IS" BASIS, -->
<#--WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. --> 
<#--See the License for the specific language governing permissions and -->
<#--limitations under the License.  -->



<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#include "../include/imports.ftl">
<#if pageable??>
<@hst.setBundle basename="essentials.pagination"/>
<ul class="pagination">
    <li class="disabled">
        <a href="#">${pageable.total}&nbsp;<@fmt.message key="results.indication" var="indication"/>${indication?html}</a>
    </li>
  <#if pageable.totalPages gt 1>
    <#list pageable.pageNumbersArray as pageNr>
        <@hst.renderURL var="pageUrl">
            <@hst.param name="page" value="${pageNr}"/>
            <@hst.param name="pageSize" value="${pageable.pageSize}"/>
        </@hst.renderURL>
        <#if (pageNr_index==0 && pageable.previous)>
            <@hst.renderURL var="pageUrlPrevious">
                <@hst.param name="page" value="${pageable.previousPage}"/>
                <@hst.param name="pageSize" value="${pageable.pageSize}"/>
            </@hst.renderURL>
            <li><a href="${pageUrlPrevious}"><@fmt.message key="page.previous" var="prev"/>${prev?html}</a></li>
        </#if>
        <#if pageable.currentPage == pageNr>
            <li class="active"><a href="#">${pageNr}</a></li>
        <#else >
            <li><a href="${pageUrl}">${pageNr}</a></li>
        </#if>

        <#if !pageNr_has_next && pageable.next>
            <@hst.renderURL var="pageUrlNext">
                <@hst.param name="page" value="${pageable.nextPage}"/>
                <@hst.param name="pageSize" value="${pageable.pageSize}"/>
            </@hst.renderURL>
            <li><a href="${pageUrlNext}"><@fmt.message key="page.next" var="next"/>${next?html}</a></li>
        </#if>
    </#list>
  </#if>
</ul>
</#if>