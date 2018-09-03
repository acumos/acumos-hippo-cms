 <#--
  ===============LICENSE_START=======================================================
  Acumos Apache-2.0
 
  Copyright (C) 2017-2018 AT&T Intellectual Property & Tech Mahindra. All rights reserved.

  This Acumos software file is distributed by AT&T and Tech Mahindra
  under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0
 
  This file is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 ===============LICENSE_END=========================================================
-->



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