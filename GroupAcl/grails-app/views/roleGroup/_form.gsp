<%@ page import="com.acl.RoleGroup" %>



<div class="fieldcontain ${hasErrors(bean: roleGroupInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="roleGroup.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${roleGroupInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roleGroupInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="roleGroup.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${roleGroupInstance?.description}"/>
</div>

