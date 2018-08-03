.. ===============LICENSE_START=======================================================
.. Acumos CC-BY-4.0
.. ===================================================================================
.. Copyright (C) 2017-2018 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
.. ===================================================================================
.. This Acumos documentation file is distributed by AT&T and Tech Mahindra
.. under the Creative Commons Attribution 4.0 International License (the "License");
.. you may not use this file except in compliance with the License.
.. You may obtain a copy of the License at
..
.. http://creativecommons.org/licenses/by/4.0
..
.. This file is distributed on an "AS IS" BASIS,
.. WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
.. See the License for the specific language governing permissions and
.. limitations under the License.
.. ===============LICENSE_END=========================================================

=============
Hippo CMS API
=============


Save Solution Revision Description
==================================


``POST http://<host and optionally port>/site/api-manual/Solution/description/{workspace}``


where "workspace" can be "public" or "org"

Request Body Example:

.. code-block:: json

    {
        "description":"<p>Test</p>",
        "solutionId":"e85f4c75-439f-4e4f-8362-6d75187f198f",
        "revisionId":"aae12a0c-ee4d-4494-b59d-493a0cc794ca"
    }


Get Solution Revision Description
=================================

``GET http://<host and optionally port>/site/api-manual/Solution/description/{workspace}/{solutionId}/{revisionId}``

where "workspace" can be "public" or "org"

Response Body Example:

.. code-block:: json

    {
        "description":"<p>Test</p>",
        "solutionId":"e85f4c75-439f-4e4f-8362-6d75187f198f"
        ,"revisionId":"aae12a0c-ee4d-4494-b59d-493a0cc794ca"
    }


Save Solution Image
===================

``POST : http://<host and optionally port>/site/api-manual/solutionImages/{solutionId}``


Request Body: Image as attachment


Get Solution Image Name
=======================

``GET http://<host and optionally port>/site/api-manual/solutionImages/{solutionId}``

Response Body: [image.jpg]

Get Binary image
================

``http://<host and optionally port>/site/binaries/content/gallery/acumoscms/solution/{solutionId}/{imageName}``


Save Solution Revision Documents
================================

``POST http://<host and optionally port>/site/api-manual/Solution/solutionAssets/{solutionId}/{revisionId}?path={workspace}``

Request Body: Document as attachment


Download Solution Revision Document
===================================

``GET http://<host and optionally port>/site/binaries/content/assets/solutiondocs/solution/{solutionId}/{RevisionId}/{workspace}/{documentName}``


Get Document Names
==================

``GET http://<host and optionally port>/site/api-manual/Solution/solutionAssets/{solutionId}/{revisionId}?path={workspace}``

Response : [Document Names]
