package app.controllers;

import app.config.HibernateConfig;
import app.daos.SampleDAO;
import app.dtos.SampleDTO;
import jakarta.persistence.EntityManagerFactory;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Map;

public class SampleController {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    SampleDAO sampleDAO = SampleDAO.getInstance(emf);

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public void createSample(Context ctx) {
        SampleDTO[] sampleDTOS = ctx.bodyAsClass(SampleDTO[].class);

        List<SampleDTO> newSampleDTOS = sampleDAO.createFromList(sampleDTOS);
        ctx.status(HttpStatus.CREATED);
        ctx.json(newSampleDTOS);
    }

    public void getAllSamples(Context ctx) {
        List<SampleDTO> sampleDTOS = sampleDAO.getAllSamples();
        ctx.status(HttpStatus.OK);
        ctx.json(sampleDTOS);
        logger.info("Fetched all samples, count: "  + sampleDTOS.size());
    }

    public void getSampleById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        SampleDTO sampleDTO = sampleDAO.getById(id);
        if (sampleDTO != null) {
            ctx.status(HttpStatus.OK);
            ctx.json(sampleDTO);
            logger.info("Fetched sample with id: " + id);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of("error", "Sample with id " + id + " not found"));
            logger.warn("Sample with id " + id + " not found");
    }
    }

    public void updateSample(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        SampleDTO sampleDTO = ctx.bodyAsClass(SampleDTO.class);
        sampleDTO = sampleDAO.update(id, sampleDTO);
        ctx.status(HttpStatus.OK);
        ctx.json(sampleDTO);
    }

    public void deleteSample(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        sampleDAO.delete(id);
        ctx.result("Sample with id " + id + " deleted");
    }
}
