package forgebiz

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ProductController {

 static scaffold = true
 
 
}
