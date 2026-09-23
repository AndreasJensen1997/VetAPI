package app.handler;

import app.dto.Patient;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientHandler {
    Map<Integer, Patient> patients;

    public PatientHandler(Map<Integer, Patient> patients){
        this.patients=patients;
    }

    public void getAllPatients(Context ctx) {
        if (patients.isEmpty()) {
            ctx.status(404);
            return;
        }
        ctx.status(200);
        ctx.json(patients.values());
    }

    public void getPatientById(Context ctx) {

        int id = Integer.parseInt(ctx.pathParam("id"));
        if (!patients.containsKey(id)) {
            ctx.status(HttpStatus.NOT_FOUND);
            return;
        }
        ctx.json(patients.get((id)));
    }
}
