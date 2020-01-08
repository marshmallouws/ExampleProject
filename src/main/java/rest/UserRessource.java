/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import errorhandling.CityNotFoundException;
import errorhandling.GenericExceptionMapper;
import errorhandling.NotUniqueException;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Annika
 */
@Path("user")
public class UserRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public Response getUsers() {
        return Response.ok().entity(FACADE.getPersons()).build();
    }
    
    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(PersonDTO user) throws NotUniqueException {
        try {
            return Response.ok().entity(GSON.toJson(FACADE.createPerson(user))).build();
        } catch (CityNotFoundException e) {
            return new GenericExceptionMapper().toResponse(e);
        }
    }
}
