/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author michal
 */
@Provider
@Priority(1)
public class EJBAccessExceptionMapper implements ExceptionMapper<EJBAccessException> {
    @Override
    public Response toResponse(EJBAccessException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                    .build();
    }
}
