
<%@ page import="com.acl.Office" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'office.label', default: 'Office')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-office" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-office" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list office">
			
				<g:if test="${officeInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="office.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${officeInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${officeInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="office.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${officeInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${officeInstance?.staff}">
				<li class="fieldcontain">
					<span id="staff-label" class="property-label"><g:message code="office.staff.label" default="Staff" /></span>
					
						<g:each in="${officeInstance.staff}" var="s">
							<span class="property-value" aria-labelledby="staff-label">
								<g:link controller="person" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link>
								<ul>
									<g:each in="${s?.authorities }" var="role">
										<li>${role.name} &raquo; ${role.description }</li>
									</g:each>
								</ul>
								
							</span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<br/><h2>Groups</h2>
			<div>
				<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'roleGroup.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'roleGroup.description.label', default: 'Description')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${officeInstance?.officeGroups}" status="i" var="roleGroupInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link controller="roleGroup" action="show" id="${roleGroupInstance.id}">${fieldValue(bean: roleGroupInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: roleGroupInstance, field: "description")}</td>
					
					</tr>
				</g:each>
				</tbody>
				</table>
			</div>
			<g:form url="[resource:officeInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${officeInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
