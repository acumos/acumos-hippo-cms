 <#-- http://www.apache.org/licenses/LICENSE-2.0-->
 
<#--This software is distributed on an "AS IS" BASIS,-->
<#--WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.-->
<#--See the License for the specific language governing permissions and-->
<#--limitations under the License.-->
 
<#--ACUMOS CC-BY-4.0-->
<#--Documentation in this repository is distributed by the authors-->
<#--under the Creative Commons Attribution 4.0 International License (the "License");-->
<#--you may not use this file except in compliance with the License.-->
<#--You may obtain a copy of the License at-->
 
<#--http://creativecommons.org/licenses/by/4.0-->
 
<#--This documentation is distributed on an "AS IS" BASIS,-->
<#--WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.-->
<#--See the License for the specific language governing permissions and-->
<#--limitations under the License.-->


<#include "../include/imports.ftl">

<#-- @ftlvariable name="query" type="java.lang.String" -->
<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#if pageable??>
  <#if pageable.total == 0>
    <h3>No results for: ${query?html}</h3>
  <#else>
    <div>
      <#list pageable.items as item>
        <#if item.title??>
          <#assign linkName=item.title/>
        <#else>
          <#assign linkName=item.localizedName/>
        </#if>
        <article>
          <@hst.cmseditlink hippobean=item/>
          <@hst.link var="link" hippobean=item />
          <h3><a href="${link}">${linkName?html}</a></h3>
        </article>
      </#list>
      <#if cparam.showPagination>
        <#include "../include/pagination.ftl">
      </#if>
    </div>
  </#if>
<#else>
  <h3>Please fill in a search term.</h3>
</#if>