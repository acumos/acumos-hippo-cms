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

<#-- @ftlvariable name="document" type="org.acumos.beans.Banner" -->
<#if document??>
  <div>
    <a href="<@hst.link hippobean=document.link />">
    <figure style="position: relative">
        <img src="<@hst.link hippobean=document.image />" alt="${document.title?html}"/>
        <figcaption style="position:absolute; top:20px; left:20px; z-index:100; color:white; background: rgba(51, 122, 183, 0.7); width:60%; padding:0 20px 20px 20px; text-shadow: 0 1px 2px rgba(0, 0, 0, .6);">
          <#if document.title??>
            <h3>${document.title?html}</h3>
          </#if>
          <@hst.html hippohtml=document.content/>
        </figcaption>
    </figure>
    </a>
  </div>
<#elseif editMode>
  <div>
    <img src="<@hst.link path='/images/essentials/catalog-component-icons/banner.png'/>"> Click to edit Banner
  </div>
</#if>
