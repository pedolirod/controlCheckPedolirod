<%--
- list.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="any.artifact.list.label.name" path="name" width="20%"/>
	<acme:list-column code="any.artifact.list.label.code" path="code" width="20%"/>
	<acme:list-column code="any.artifact.list.label.description" path="description" width="30%"/>
	<acme:list-column code="any.artifact.list.label.retail-price" path="retailPrice" width="10%"/>
	<acme:list-column code="any.artifact.list.label.link" path="link" width="10%"/>
</acme:list>
<jstl:choose>
	<jstl:when test="${isIngredient}">
		<acme:button code="any.arifact.list.button.create" action="/chef/artifact/create-ingredient"/>
	</jstl:when>
	<jstl:otherwise>
		<acme:button code="any.arifact.list.button.create" action="/chef/artifact/create-utensil"/>
	</jstl:otherwise>
</jstl:choose>