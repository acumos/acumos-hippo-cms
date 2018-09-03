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

<#-- @ftlvariable name="item" type="org.acumos.beans.Blogpost" -->
<#-- @ftlvariable name="author" type="org.acumos.beans.Author" -->
<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->

<@hst.setBundle basename="essentials.blog"/>
<#if pageable??>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h3 class="panel-title"><@fmt.message key="blog.moreby" var="moreby"/>${moreby?html}&nbsp;${author.fullName?html}</h3>
    </div>
    <#if pageable?? && (pageable.total > 0)>
      <div class="panel-body">
        <#list pageable.items as item>
          <@hst.link hippobean=item var="link"/>
          <p><a href="${link}">${item.title?html}</a></p>
        </#list>
      </div>
    <#else>
      <div class="panel-body">
        <p><@fmt.message key="blog.notfound" var="notfound"/>${notfound?html}</p>
      </div>
    </#if>
  </div>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path='/images/essentials/catalog-component-icons/blogposts-by-author.png'/>"> Click to edit Blogposts by Author
  </div>
</#if>
