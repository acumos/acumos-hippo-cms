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



<#include "../../include/imports.ftl">

<#-- @ftlvariable name="item" type="org.acumos.beans.NewsDocument" -->
<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#-- @ftlvariable name="cparam" type="org.onehippo.cms7.essentials.components.info.EssentialsNewsComponentInfo" -->
<#if pageable?? && pageable.items?has_content>
  <div>
    <#list pageable.items as item>
      <@hst.link var="link" hippobean=item />
      <@hst.cmseditlink hippobean=item/>
    <div class="media">
      <div class="media-left" style="float: left">
        <a href="${link}">
          <#if item.image?? && item.image.thumbnail??>
            <@hst.link var="img" hippobean=item.image.thumbnail/>
            <img src="${img}" title="${item.image.fileName?html}" alt="${item.image.fileName?html}"/>
          </#if>
        </a>
      </div>
      <div class="media-body">
        <h4 class="media-heading"><a href="${link}">${item.title?html}</a>
          <span class="label label-success pull-right">
            <#if item.date?? && item.date.time??>
              <@fmt.formatDate value=item.date.time type="both" dateStyle="medium" timeStyle="short"/>
            </#if>
          </span>
        </h4>
        <p>${item.introduction?html}</p>
      </div>
    </div>
    </#list>
    <#if cparam.showPagination>
      <#include "../../include/pagination.ftl">
    </#if>
  </div>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path='/images/essentials/catalog-component-icons/news-list.png'/>"> Click to edit News List
  </div>
</#if>


