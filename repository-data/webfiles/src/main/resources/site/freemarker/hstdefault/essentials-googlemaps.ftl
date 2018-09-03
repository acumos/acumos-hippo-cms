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

<#-- @ftlvariable name="componentId" type="java.lang.String" -->
<#-- @ftlvariable name="cparam" type="org.onehippo.cms7.essentials.components.info.EssentialsGoogleMapsComponentInfo" -->
<div>
    <div id="map-canvas-${componentId}" style="width: ${cparam.width}px; height: ${cparam.height}px;"></div>

    <@hst.headContribution category="htmlBodyEnd">
        <script type="text/javascript">
            (function(win) {
                var he, gm;
                if (!win.HippoEssentials) {
                    win.HippoEssentials = {};
                }
                he = win.HippoEssentials;

                if (!he.GoogleMaps) {
                    he.GoogleMaps = {
                        queue: []
                    };
                }
                gm = he.GoogleMaps;

                gm.queue.push(function() {
                    gm.render('map-canvas-${componentId}', '${cparam.address?html}', ${cparam.longitude}, ${cparam.latitude}, ${cparam.zoomFactor}, '${cparam.mapType}', '${cparam.apiKey}');
                });
            })(window);
        </script>
    </@hst.headContribution>

    <@hst.headContribution keyHint="essentials-google-maps" category="htmlBodyEnd">
        <@hst.webfile path="/js/essentials-google-maps.js" var="essentialsGoogleMapsJs" />
        <script type="text/javascript" src="${essentialsGoogleMapsJs}"></script>
    </@hst.headContribution>

    <@hst.headContribution keyHint="google-maps-api" category="htmlBodyEnd">
        <#if cparam.apiKey?has_content>
            <#assign mapsUrl = "https://maps.googleapis.com/maps/api/js?key=${cparam.apiKey?html}&amp;callback=HippoEssentials.GoogleMaps.init"/>
        <#else>
            <#assign mapsUrl = "https://maps.googleapis.com/maps/api/js?callback=HippoEssentials.GoogleMaps.init"/>
        </#if>
        <script type="text/javascript" src="${mapsUrl}" async="async" defer="defer"></script>
    </@hst.headContribution>

    <#if editMode>
        <script type="text/javascript">
            if (window.HippoEssentials && window.HippoEssentials.GoogleMaps) {
                window.HippoEssentials.GoogleMaps.render('map-canvas-${componentId}', '${cparam.address?html}', ${cparam.longitude}, ${cparam.latitude}, ${cparam.zoomFactor}, '${cparam.mapType}');
            }
        </script>
    </#if>
</div>