import com.acl.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder as SCH

class BootStrap {
def groupManagerService
    def init = { servletContext ->
		initRequestmap()
		createUsers()
		
	//	loginAsAdmin()
		
		// logout
	//	SCH.clearContext()
    }
    def destroy = {
		
    }
	
	private boolean createUsers() {
		//Create office and person
		def personAdmin = new Person(firstName:"Sys",lastName:"Sysuser")
		def personDev = new Person(firstName:"Sys",lastName:"Devuser")
		def mainOffice = new Office(name:"Main Office",code:"NHO")
		mainOffice.addToStaff(personAdmin)
		mainOffice.addToStaff(personDev)
		mainOffice.save(flush:true)
		if(mainOffice.hasErrors()) {
			println mainOffice.errors
			return false;
		}
		
		try{
			//create ROLES
		//	new Role(authority:"ROLE_ADMINTEST",description:"test description").save(flush:true)
			groupManagerService.generateRoles()
			groupManagerService.generateOfficeGroups(mainOffice)
			
			println ">> find a admin and dev roles"
			def adminRole = Role.findByAuthority(SystemRoles.ROLE_ADMIN.value)	// new Role(authority:"ROLE_ADMIN").save(flush:true)
			def devRole = Role.findByAuthority(SystemRoles.ROLE_DEVELOPER.value) //new Role(authority:"ROLE_USER").save(flush:true)
			
			//SYSTEM ADMIN group(s)
			def adminGroup = new RoleGroup(name:"GROUP_ADMIN",description:"Administrators").save(flush:true)
			def devGroup = new RoleGroup(name:"GROUP_DEVELOPER",description:"Developers").save(flush:true)
			
			println ">> creating RoleGroupRoles..."
			RoleGroupRole.create adminGroup, adminRole
			RoleGroupRole.create devGroup, devRole
			
			1.times {
				long id = it + 1
				def user = new User(username: "dev$id", enabled: true, password: "dev$id",person:personDev).save(flush:true)
				//UserRoleGroup.create user, devGroup
				println ">> Adding dev user to office groups"
				groupManagerService.addUserToGroup(user, mainOffice, SystemRoles.list())
			}
		
			println ">> Creating admin user..."
			def admin = new User(username: 'admin', enabled: true, password: 'admin123',person:personAdmin).save(flush:true)
		
			UserRoleGroup.create admin, devGroup
			UserRoleGroup.create admin, adminGroup, true
			
			//ADD Admin user to list of roles
			println ">> Adding admin to office groups"
			groupManagerService.addUserToGroup(admin, mainOffice, [SystemRoles.ROLE_OCO,SystemRoles.ROLE_CWO])
		}catch(Exception e){
			println "Error " + e
			return false
		}
		
		return true
	} //end create users
	private void loginAsAdmin() {
		// have to be authenticated as an admin to create ACLs
		SCH.context.authentication = new UsernamePasswordAuthenticationToken( 'admin', 'admin123', AuthorityUtils.createAuthorityList('ROLE_ADMIN'))
	}
	
	private void initRequestmap(){
		
		for (String url in [ '/',
			 '/index',
			 '/index.gsp',
			 '/**/favicon.ico',
			 '/**/js/**',
			 '/**/css/**',
			 '/**/images/**',
			 '/login',
			 '/login.*',
			 '/login/*',
			 '/logout',
			 '/logout.*',
			 '/secure/**',
			 '/logout/*']) {
			 new Requestmap( url: url, configAttribute: 'permitAll').save()
		}
			 // show and lists/index
			 for (String url in [
				 '/acl/**/**',
				 '/**/show/**',
				  '/**/index/**']) {
				  new Requestmap( url: url, configAttribute:'isFullyAuthenticated()').save()
			 }
		
			 //editing for office admin
			 for (String url in [ 
				 '/**/create/**',
				 '/**/save/**',
				 '/**/update/**',
				 '/**/edit/**',]) {
				  new Requestmap( url: url, configAttribute:  SystemRoles.ROLE_ADMIN.value + ',' + SystemRoles.ROLE_OCO.value).save()
			 }
			 
			
			 //strictly admin
			 for (String url in [ '/requestmap/**',
				 '/role/**',
				 '/roleGroup/**',				 
				 '/user/**']) {
				 new Requestmap( url: url, configAttribute: SystemRoles.ROLE_ADMIN.value).save()
			}
	} //end method
} //
