package com.ubal.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ubal.test.service.TareaService;
import com.ubal.test.web.rest.errors.BadRequestAlertException;
import com.ubal.test.web.rest.util.HeaderUtil;
import com.ubal.test.service.dto.TareaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tarea.
 */
@RestController
@RequestMapping("/api")
public class TareaResource {

    private final Logger log = LoggerFactory.getLogger(TareaResource.class);

    private static final String ENTITY_NAME = "tarea";

    private final TareaService tareaService;

    public TareaResource(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    /**
     * POST  /tareas : Create a new tarea.
     *
     * @param tareaDTO the tareaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tareaDTO, or with status 400 (Bad Request) if the tarea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tareas")
    @Timed
    public ResponseEntity<TareaDTO> createTarea(@Valid @RequestBody TareaDTO tareaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarea : {}", tareaDTO);
        if (tareaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TareaDTO result = tareaService.save(tareaDTO);
        return ResponseEntity.created(new URI("/api/tareas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tareas : Updates an existing tarea.
     *
     * @param tareaDTO the tareaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tareaDTO,
     * or with status 400 (Bad Request) if the tareaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tareaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tareas")
    @Timed
    public ResponseEntity<TareaDTO> updateTarea(@Valid @RequestBody TareaDTO tareaDTO) throws URISyntaxException {
        log.debug("REST request to update Tarea : {}", tareaDTO);
        if (tareaDTO.getId() == null) {
            return createTarea(tareaDTO);
        }
        TareaDTO result = tareaService.save(tareaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tareaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tareas : get all the tareas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tareas in body
     */
    @GetMapping("/tareas")
    @Timed
    public List<TareaDTO> getAllTareas() {
        log.debug("REST request to get all Tareas");
        return tareaService.findAll();
        }

    /**
     * GET  /tareas/:id : get the "id" tarea.
     *
     * @param id the id of the tareaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tareaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tareas/{id}")
    @Timed
    public ResponseEntity<TareaDTO> getTarea(@PathVariable Long id) {
        log.debug("REST request to get Tarea : {}", id);
        TareaDTO tareaDTO = tareaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tareaDTO));
    }

    /**
     * DELETE  /tareas/:id : delete the "id" tarea.
     *
     * @param id the id of the tareaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tareas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        log.debug("REST request to delete Tarea : {}", id);
        tareaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
