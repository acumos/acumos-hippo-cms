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
