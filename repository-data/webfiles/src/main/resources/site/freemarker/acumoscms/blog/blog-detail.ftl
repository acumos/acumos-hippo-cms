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

<#-- @ftlvariable name="document" type="org.acumos.beans.Blogpost" -->
<#if document??>
<div class="has-edit-button">
<@hst.cmseditlink hippobean=document/>
<h1>${document.title?html}</h1>
<h2>by: ${document.author?html}</h2>
<strong>
    <#if document.publicationDate??>
        <@fmt.formatDate type="date" pattern="yyyy-MM-dd" value=document.publicationDate.time/>
    </#if>
</strong>
<p>${document.introduction?html}</p>
<div>
    <@hst.html hippohtml=document.content />
</div>
</div>
</#if>