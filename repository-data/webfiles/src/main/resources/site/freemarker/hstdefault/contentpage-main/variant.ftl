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

<#-- @ftlvariable name="document" type="org.acumos.beans.ContentDocument" -->
<#if document??>
  <article class="has-edit-button">
    <@hst.cmseditlink hippobean=document/>
    <h3>${document.title?html}
      <#if document.publicationDate??>
        <small><@fmt.formatDate value=document.publicationDate.time type="date" dateStyle="medium"/></small>
      </#if>
    </h3>
    <#if document.introduction??>
      <p class="lead">${document.introduction?html}</p>
    </#if>
    <@hst.html hippohtml=document.content/>
  </article>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.png" />"> Click to edit Simple Content
  </div>
</#if>