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



<#include "../../../include/imports.ftl">

<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#if pageable?? && pageable?has_content>
  <div>
    <#list pageable.items as item>
      <div class="media has-edit-button">
        <@hst.cmseditlink hippobean=item/>
        <div class="media-body">
          <h4 class="media-heading">
            <@hst.link var="link" hippobean=item />
            <a href="${link}">${item.title?html}</a>
            <#if item.publicationDate??>
              <span class="label label-success pull-right">
                <@fmt.formatDate value=item.publicationDate.time type="both" dateStyle="medium" timeStyle="short"/>
              </span>
            </#if>
          </h4>
          <#if item.introduction??>
            <p>${item.introduction?html}</p>
          </#if>
        </div>
      </div>
    </#list>
    <#if cparam.showPagination>
      <#include "../../../include/pagination.ftl">
    </#if>
  </div>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path='/images/essentials/catalog-component-icons/blog-list.png'/>"> Click to edit Blog List
  </div>
</#if>