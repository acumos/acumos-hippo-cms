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

<#if title??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.document.title">
  <title>${title?xml}</title>
  </@hst.headContribution>
</#if>

<#if metaDescription??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.document.description">
  <meta name="description" content="${metaDescription?xml}"/>
  </@hst.headContribution>
</#if>

<#if dublinCoreSchemaLink??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.schema.dc">
  <link rel="schema.DC" href="${dublinCoreSchemaLink?xml}"/>
  </@hst.headContribution>
</#if>

<#if dublinCoreTermsLink??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.dc.terms">
  <link rel="schema.DCTERMS" href="${dublinCoreTermsLink?xml}"/>
  </@hst.headContribution>
</#if>

<#if dublinCoreCopyrightLink??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.dc.copyright">
  <link rel="DC.rights copyright" href="${dublinCoreCopyrightLink?xml}"/>
  </@hst.headContribution>
</#if>

<#if dublinCoreLanguage??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.dc.language">
  <meta scheme="DCTERMS.RFC3066" name="DC.language" content="${dublinCoreLanguage?xml}"/>
  </@hst.headContribution>
</#if>

<#if dublinCoreTermsCreated??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.dc.terms.created">
  <meta name="DCTERMS.created" content="${dublinCoreTermsCreated?xml}"/>
  </@hst.headContribution>
</#if>

<#if dublinCoreTermsModified??>
  <@hst.headContribution category="SEO" keyHint="hst.seo.dc.terms.modified">
  <meta name="DCTERMS.modified" content="${dublinCoreTermsModified?xml}"/>
  </@hst.headContribution>
</#if>

<#if hstRequest.requestContext.cmsRequest>
  <div>
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/seo.png" />"> Click to edit SEO parameters
  </div>
</#if>