<!doctype html>
<!---
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





<%@ page isErrorPage="true" %>
<% response.setStatus(500); %>

<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <title>500 error</title>
  </head>
  <body>
    <h1>Server error</h1>
    <% out.println("<!-- An unexcepted error occurred. The name of the exception is:"); %>
    <%= exception %>
    <% out.println("-->"); %>
  </body>
</html>
