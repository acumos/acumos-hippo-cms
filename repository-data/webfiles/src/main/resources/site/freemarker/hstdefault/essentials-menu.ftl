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

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#if menu??>
  <div class="has-edit-button">
    <ul class="nav nav-pills">
      <#list menu.siteMenuItems as item>
        <#if !item.hstLink?? && !item.externalLink??>
          <#if item.selected || item.expanded>
            <li class="active">
              <div style="padding: 10px 15px;">${item.name?html}</div>
            </li>
          <#else>
            <li>
              <div style="padding: 10px 15px;">${item.name?html}</div>
            </li>
          </#if>
        <#else>
          <#if item.hstLink??>
            <#assign href><@hst.link link=item.hstLink/></#assign>
          <#elseif item.externalLink??>
            <#assign href>${item.externalLink?replace("\"", "")}</#assign>
          </#if>
          <#if  item.selected || item.expanded>
            <li class="active"><a href="${href}">${item.name?html}</a></li>
          <#else>
            <li><a href="${href}">${item.name?html}</a></li>
          </#if>
        </#if>
      </#list>
    </ul>
    <@hst.cmseditmenu menu=menu/>
  </div>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/menu.png" />"> Click to edit Menu
  </div>
</#if>