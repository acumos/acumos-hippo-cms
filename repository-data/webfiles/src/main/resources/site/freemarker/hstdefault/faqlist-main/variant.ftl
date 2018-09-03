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

<#-- @ftlvariable name="document" type="org.acumos.beans.FaqList" -->
<#if document??>
  <#if document.FAQ??>
    <div class="has-edit-button">
      <@hst.cmseditlink hippobean=document/>
      <h1>${document.title?html}</h1>
      <@hst.html hippohtml=document.description/>
      <div class="panel-group" id="faqitems" role="tablist" aria-multiselectable="true">
        <#list document.faqItems as faq>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="heading${faq_index}">
              <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#faqitems" href="#faq_${faq_index}" aria-expanded="false" aria-controls="collapse${faq_index}">
                  ${faq.question?html}
                </a>
              </h4>
            </div>
            <div id="faq_${faq_index}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${faq_index}">
              <div class="panel-body">
                <@hst.html hippohtml=faq.answer/>
              </div>
            </div>
          </div>
        </#list>
      </div>
    </div>
    <@hst.headContribution category="htmlBodyEnd">
      <script type="text/javascript" src="<@hst.webfile path="/js/jquery-2.1.0.min.js"/>"></script>
    </@hst.headContribution>
    <@hst.headContribution category="htmlBodyEnd">
      <script type="text/javascript" src="<@hst.webfile path="/js/bootstrap.min.js"/>"></script>
    </@hst.headContribution>
  <#else>
    <div class="alert alert-danger">The selected document should be of type FAQ list.</div>
  </#if>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/faq.png" />"> Click to edit FAQ
  </div>
</#if>