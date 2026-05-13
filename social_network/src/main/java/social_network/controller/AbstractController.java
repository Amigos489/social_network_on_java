package social_network.controller;

import social_network.service.ServiceFacade;

public abstract class AbstractController {

    protected ServiceFacade serviceFacade;

    public AbstractController(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }
}
