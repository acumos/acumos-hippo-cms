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
  ===============LICENSE_START=======================================================
-->


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