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


<#include "../../../include/imports.ftl">

<#-- @ftlvariable name="document" type="org.acumos.beans.Blogpost" -->
<#if document??>
    <div class="has-edit-button">
        <@hst.cmseditlink hippobean=document/>
        <h1>
            ${document.title?html}
            <#if document.publicationDate??>
                <small><@fmt.formatDate type="date" pattern="yyyy-MM-dd" value=document.publicationDate.time/></small>
            </#if>
        </h1>
        <#if document.introduction??>
            <p class="lead">${document.introduction?html}</p>
        </#if>
        <@hst.html hippohtml=document.content />
        <#if document.author??>
            <footer>
                <p>-- by <em>${document.author?html}</em></p>
            </footer>
        </#if>
    </div>
</#if>