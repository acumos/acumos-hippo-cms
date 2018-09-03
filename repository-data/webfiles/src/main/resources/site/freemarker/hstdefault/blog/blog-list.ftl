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




<#include "../../include/imports.ftl">

<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#if pageable??>
  <div>
    <#list pageable.items as item>
      <@hst.link var="link" hippobean=item />
      <article class="has-edit-button">
        <@hst.cmseditlink hippobean=item/>
        <h3><a href="${link}">${item.title?html}</a></h3>
        <#if item.publicationDate?? && item.publicationDate.time??>
          <p><@fmt.formatDate value=item.publicationDate.time type="both" dateStyle="medium" timeStyle="short"/></p>
        </#if>
        <p>${item.introduction?html}</p>
      </article>
    </#list>
    <#if cparam.showPagination>
     <#include "../../include/pagination.ftl">
    </#if>
  </div>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path='/images/essentials/catalog-component-icons/blog-list.png'/>"> Click to edit Blog List
  </div>
</#if>