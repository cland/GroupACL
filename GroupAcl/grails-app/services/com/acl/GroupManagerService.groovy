package com.acl

import grails.transaction.Transactional

@Transactional
class GroupManagerService {
	def springSecurityService
    def generateOfficeGroups(Office office) {
		println "... Generating Office Groups..."
		def _tmp = "GROUP_" + office?.code?.toString()?.toUpperCase()?.replace(" ","_") 
		def _tmp_desc = office?.name?.toString()

		
		def officeAdminGroup = new RoleGroup(name: _tmp + "_" + SystemRoles.ROLE_OCO.getKey(),description:_tmp_desc + " - Admins" ).save(flush:true)		
		def officeWorkerGroup = new RoleGroup(name:_tmp + "_" + SystemRoles.ROLE_CWO.getKey(),description:_tmp_desc + " - Case Workers").save(flush:true)
		def officeSPGroup = new RoleGroup(name:_tmp + "_" + SystemRoles.ROLE_OCO.getKey(),description:_tmp_desc + " - Special Workers").save(flush:true)
		def officeReaderGroup = new RoleGroup(name:_tmp + "_" + SystemRoles.ROLE_OCO.getKey(),description:_tmp_desc + " - Readers").save(flush:true)
		
		RoleGroupRole.create officeAdminGroup, Role.findByAuthority(SystemRoles.ROLE_OCO.value)
		RoleGroupRole.create officeWorkerGroup, Role.findByAuthority(SystemRoles.ROLE_CWO.value)
		RoleGroupRole.create officeSPGroup, Role.findByAuthority(SystemRoles.ROLE_SPO.value)
		RoleGroupRole.create officeReaderGroup, Role.findByAuthority(SystemRoles.ROLE_REVIEWER.value)
		
		println "... Done generating Office Groups..."
    }// end generateGroups
	 	
	def removeOfficeGroups(Office office){
		def _tmp = "GROUP_" + office?.code?.toString()?.toUpperCase()?.replace(" ","_")
		def _tmp_desc = office?.name?.toString()
		def officeAdminGroup = RoleGroup.findByName(_tmp + "_" + SystemRoles.ROLE_OCO.getKey())
		def officeWorkerGroup = RoleGroup.findByName(name:_tmp + "_" + SystemRoles.ROLE_CWO.getKey())
		def officeSPGroup = RoleGroup.findByName(name:_tmp + "_" + SystemRoles.ROLE_SPO.getKey())
		def officeReaderGroup = RoleGroup.findByName(name:_tmp + "_" + SystemRoles.ROLE_REVIEWER.getKey())
		
		RoleGroupRole.remove officeAdminGroup, Role.findByAuthority(SystemRoles.ROLE_OCO.value)
		RoleGroupRole.remove officeWorkerGroup, Role.findByAuthority(SystemRoles.ROLE_CWO.value)
		RoleGroupRole.remove officeSPGroup, Role.findByAuthority(SystemRoles.ROLE_SPO.value)
		RoleGroupRole.remove officeReaderGroup, Role.findByAuthority(SystemRoles.ROLE_REVIEWER.value)
		
		officeAdminGroup?.delete flush:true
		officeWorkerGroup?.delete flush:true
		officeSPGroup?.delete flush:true
		officeReaderGroup?.delete flush:true
	}
	/**
	 * On user for, expect to select roles for user and then based on those roles add to the given office groups
	 * @param user
	 * @param office
	 * @param roles
	 * @return
	 */
	def addUserToGroup(User user,Office office,List roles){
		def _tmp = "GROUP_" + office?.code?.toString()?.toUpperCase()?.replace(" ","_")
		roles.each{role ->
			def group = RoleGroup.findByName(_tmp + "_" +role.getKey())
			if(group)UserRoleGroup.create user, group
		}		
	}
	
	def generateRoles(){
		println "... Generating ROLES..."
	//	def adminRole = new Role(authority:"ROLE_ADMIN22",description:"").save(flush:true)
	//	println(adminRole)
		def tmp = SystemRoles.list()
		
		tmp.each{item ->
			println(item?.value + " - " + item?.description)
			new Role(authority:item?.value,description:item?.description).save(flush:true)
		}
		println "... DONE Generating ROLES!"
	}
} //END CLASS
