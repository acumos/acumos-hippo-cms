
<%--  
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

    
      --%>


<!doctype html>
<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>

<fmt:setBundle basename="org.hippoecm.hst.security.servlet.LoginServlet"/>

<%
  String destination = "";
  HttpSession session = pageContext.getSession();
  if (session != null) {
    destination = (String) session.getAttribute("org.hippoecm.hst.security.servlet.destination");
    if (destination == null) destination = "";
  }
  int autoRedirectSeconds = 2;
%>

<hst:link var="loginFormUrl" path="/login/form">
  <hst:param name="destination" value="<%=destination%>"/>
</hst:link>
<hst:link var="loginProxyUrl" path="/login/proxy">
  <hst:param name="destination" value="<%=destination%>"/>
</hst:link>

<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title><fmt:message key="label.authen.required"/></title>
  <meta http-equiv="refresh" content='<%=autoRedirectSeconds%>;url=${loginFormUrl}'/>
  <link rel="stylesheet" type="text/css" href="<hst:link path='/login/hst/security/skin/screen.css'/>"/>
</head>
<body class="hippo-root">
<div>
  <div class="hippo-login-panel">
    <form class="hippo-login-panel-form" name="signInForm" method="post" action="${loginProxyUrl}">
      <h2>
        <div class="hippo-global-hideme"><span>Hippo CMS</span></div>
      </h2>
      <div class="hippo-login-form-container">
        <table>
          <tr>
            <td>
              <p>
                <fmt:message key="message.authen.required">
                  <fmt:param value="<%=destination%>"/>
                </fmt:message>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <a href="${loginFormUrl}"><fmt:message key="message.try.again"/></a>
                <br/><br/>
                <fmt:message key="message.page.auto.redirect.in.seconds">
                  <fmt:param value="<%=autoRedirectSeconds%>"/>
                </fmt:message>
              </p>
            </td>
          </tr>
        </table>
      </div>
    </form>
    <div class="hippo-login-panel-copyright">
      &copy; 1999-2014 Hippo B.V.
    </div>
  </div>
</div>
</body>
</html>