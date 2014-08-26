package com.acl



import grails.test.mixin.*
import spock.lang.*

@TestFor(OfficeController)
@Mock(Office)
class OfficeControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.officeInstanceList
            model.officeInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.officeInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def office = new Office()
            office.validate()
            controller.save(office)

        then:"The create view is rendered again with the correct model"
            model.officeInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            office = new Office(params)

            controller.save(office)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/office/show/1'
            controller.flash.message != null
            Office.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def office = new Office(params)
            controller.show(office)

        then:"A model is populated containing the domain instance"
            model.officeInstance == office
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def office = new Office(params)
            controller.edit(office)

        then:"A model is populated containing the domain instance"
            model.officeInstance == office
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/office/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def office = new Office()
            office.validate()
            controller.update(office)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.officeInstance == office

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            office = new Office(params).save(flush: true)
            controller.update(office)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/office/show/$office.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/office/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def office = new Office(params).save(flush: true)

        then:"It exists"
            Office.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(office)

        then:"The instance is deleted"
            Office.count() == 0
            response.redirectedUrl == '/office/index'
            flash.message != null
    }
}
