package forgebiz

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ProductAttributeValueController {

 static scaffold = true
}
