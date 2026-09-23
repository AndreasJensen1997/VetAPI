package app;

import app.dto.Appointment;
import app.dto.Patient;
import app.handler.AppointmentHandler;
import app.handler.PatientHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {
    static void main() {

        // Javalin serer
        // 1. Create and configure your ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                mapper.registerModule(new JavaTimeModule());
            }));
        });
        app.start(7070);


        //handlers
        PatientHandler patientHandler = new PatientHandler(createSamplePatients());
        AppointmentHandler appointmentHandler = new AppointmentHandler(createSampleAppointments(createSamplePatients()));

        Map<Integer, Appointment> appointmentMap = createSampleAppointments(createSamplePatients());

        System.out.println(appointmentMap);


        //register routes

        //get all
        app.get("/api/v1/vet/patients", patientHandler::getAllPatients);
        //get
        app.get("/api/v1/vet/patients/{id}", patientHandler::getPatientById);


        //get all
        app.get("/api/v1/vet/appointments", appointmentHandler::getAllAppointments);
        //get
        app.get("/api/v1/vet/appointments/{id}", appointmentHandler::getAppointmentById);
    }

    public static Map<Integer, Patient> createSamplePatients() {
        return Map.of(
                1, new Patient(
                        1,
                        "John Doe",
                        List.of("Hypertension", "Type 2 Diabetes"),
                        List.of("Penicillin", "Peanuts"),
                        List.of("Metformin", "Lisinopril"),
                        "Patient requires quarterly blood sugar monitoring and regular blood pressure checks."
                ),
                2, new Patient(
                        2,
                        "Jane Smith",
                        List.of("Asthma", "Migraine"),
                        List.of("Sulfa drugs", "Latex"),
                        List.of("Albuterol", "Sumatriptan"),
                        "Carries an rescue inhaler. Patient reported frequent seasonal flare-ups."
                ),
                3, new Patient(
                        3,
                        "Michael Johnson",
                        List.of("Gastroesophageal Reflux Disease (GERD)"),
                        List.of("Aspirin"),
                        List.of("Omeprazole"),
                        "Prefers morning appointments. Mild persistent indigestion managed with diet and medication."
                ),
                4, new Patient(
                        4,
                        "Emily Davis",
                        List.of("Hypothyroidism", "Anemia"),
                        List.of(), // No known allergies
                        List.of("Levothyroxine", "Iron Supplements"),
                        "Follow-up blood work scheduled in 3 months to monitor TSH levels."
                ),
                5, new Patient(
                        5,
                        "Robert Brown",
                        List.of("Osteoarthritis", "Hyperlipidemia"),
                        List.of("Ibuprofen", "Codeine"),
                        List.of("Atorvastatin", "Acetaminophen"),
                        "Physical therapy recommended twice weekly for knees."
                ),
                6, new Patient(
                        6,
                        "Sophia Wilson",
                        List.of("Generalized Anxiety Disorder", "Insomnia"),
                        List.of("Shellfish"),
                        List.of("Sertraline", "Melatonin"),
                        "Responding well to ongoing cognitive behavioral therapy and current dosage."
                )
        );
    }

    public static Map<Integer, Appointment> createSampleAppointments(Map<Integer, Patient> patientsMap){

        List<Patient> patients = patientsMap.values().stream().toList();

        Patient patient1 = patients.get(0);
        Patient patient2 = patients.get(1);
        Patient patient3 = patients.get(2);
        Patient patient4 = patients.get(3);
        Patient patient5 = patients.get(4);
        Patient patient6 = patients.get(5);

        // Appointment Examples
        Appointment appointment1 = new Appointment(
                1,
                patient1,
                LocalDateTime.of(2026, 10, 15, 9, 30),
                "Annual Wellness Exam"
        );

        Appointment appointment2 = new Appointment(
                2,
                patient2,
                LocalDateTime.of(2026, 10, 15, 11, 0),
                "Quarterly Diabetes and Blood Pressure Follow-up"
        );

        Appointment appointment3 = new Appointment(
                3,
                patient3,
                LocalDateTime.of(2026, 10, 16, 14, 15),
                "Acute Knee Pain Consultation"
        );

        Appointment appointment4 = new Appointment(
                4,
                patient4,
                LocalDateTime.of(2026, 9, 16, 11, 15),
                "Shoulder pain"
        );

        Appointment appointment5 = new Appointment(
                5,
                patient5,
                LocalDateTime.of(2026, 2, 16, 10, 45),
                "Needs to have balls checked"
        );

        Appointment appointment6 = new Appointment(
                6,
                patient6,
                LocalDateTime.of(2026, 5, 10, 13, 30),
                "Chlamydia test"
        );

        return Map.of(
                1, appointment1, 2,appointment2, 3,appointment3, 4,appointment4, 5,appointment5, 6,appointment6
        );
    }

}
