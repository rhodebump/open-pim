package forgebiz

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ConstraintController {

 static scaffold = true
}
