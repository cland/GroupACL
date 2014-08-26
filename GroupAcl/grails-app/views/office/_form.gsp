<%@ page import="com.acl.Office" %>



<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="office.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${officeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'code', 'error')} required">
	<label for="code">
		<g:message code="office.code.label" default="Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="code" required="" value="${officeInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'staff', 'error')} ">
	<label for="staff">
		<g:message code="office.staff.label" default="Staff" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${officeInstance?.staff?}" var="s">
    <li><g:link controller="person" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="person" action="create" params="['office.id': officeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'person.label', default: 'Person')])}</g:link>
</li>
</ul>

</div>

