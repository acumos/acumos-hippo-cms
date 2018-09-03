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

<#-- @ftlvariable name="document" type="org.acumos.beans.EventsDocument" -->
<#if document??>
    <@hst.link var="link" hippobean=document/>
<article class="has-edit-button">
    <@hst.cmseditlink hippobean=document/>
    <h3><a href="${link}">${document.title?html}</a></h3>
    <#if document.date??>
        <p><@fmt.formatDate value=document.date.time type="both" dateStyle="medium" timeStyle="short"/></p>
    </#if>
    <#if document.endDate??>
        <p><@fmt.formatDate value=document.endDate.time type="both" dateStyle="medium" timeStyle="short"/></p>
    </#if>
    <#if document.location??>
        <p>${document.location?html}</p>
    </#if>
    <#if document.introduction??>
        <p>${document.introduction?html}</p>
    </#if>
    <#if document.image?? && document.image.original??>
        <@hst.link var="img" hippobean=document.image.original/>
        <figure>
            <img src="${img?html}" title="${document.image.fileName?html}" alt="${document.image.fileName?html}"/>
            <#if document.image.description??>
                <figcaption>${document.image.description?html}</figcaption>
            </#if>
        </figure>
    </#if>
    <@hst.html hippohtml=document.content/>
</article>
</#if>