package app.handler;

import app.dto.Appointment;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Map;

public class AppointmentHandler {

    Map<Integer, Appointment> appointments;

    public AppointmentHandler(Map<Integer, Appointment> appointments){
        this.appointments = appointments;
    }


    public void getAllAppointments(Context ctx){
        if (appointments.isEmpty()) {
            ctx.status(404);
            return;
        }
        ctx.status(200);
        ctx.json(appointments.values());
    }

    public void getAppointmentById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        if (!appointments.containsKey(id)) {
            ctx.status(HttpStatus.NOT_FOUND);
            return;
        }
        ctx.json(appointments.get((id)));

    }


}
