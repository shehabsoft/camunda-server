package com.emu.camunda.web.rest;

import com.emu.camunda.service.BpmnFilesService;
import com.emu.camunda.web.rest.errors.BadRequestAlertException;
import com.emu.camunda.service.dto.BpmnFilesDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emu.camunda.domain.BpmnFiles}.
 */
@RestController
@RequestMapping("/api")
public class BpmnFilesResource {

    private final Logger log = LoggerFactory.getLogger(BpmnFilesResource.class);

    private static final String ENTITY_NAME = "camundaServerBpmnFiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BpmnFilesService bpmnFilesService;

    public BpmnFilesResource(BpmnFilesService bpmnFilesService) {
        this.bpmnFilesService = bpmnFilesService;
    }

    /**
     * {@code POST  /bpmn-files} : Create a new bpmnFiles.
     *
     * @param bpmnFilesDTO the bpmnFilesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bpmnFilesDTO, or with status {@code 400 (Bad Request)} if the bpmnFiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bpmn-files")
    public ResponseEntity<BpmnFilesDTO> createBpmnFiles(@RequestBody BpmnFilesDTO bpmnFilesDTO) throws URISyntaxException {
        log.debug("REST request to save BpmnFiles : {}", bpmnFilesDTO);
        if (bpmnFilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new bpmnFiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BpmnFilesDTO result = bpmnFilesService.save(bpmnFilesDTO);
        return ResponseEntity.created(new URI("/api/bpmn-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bpmn-files} : Updates an existing bpmnFiles.
     *
     * @param bpmnFilesDTO the bpmnFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmnFilesDTO,
     * or with status {@code 400 (Bad Request)} if the bpmnFilesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bpmnFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bpmn-files")
    public ResponseEntity<BpmnFilesDTO> updateBpmnFiles(@RequestBody BpmnFilesDTO bpmnFilesDTO) throws URISyntaxException {
        log.debug("REST request to update BpmnFiles : {}", bpmnFilesDTO);
        if (bpmnFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BpmnFilesDTO result = bpmnFilesService.save(bpmnFilesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bpmnFilesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bpmn-files} : get all the bpmnFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bpmnFiles in body.
     */
    @GetMapping("/bpmn-files")
    public ResponseEntity<List<BpmnFilesDTO>> getAllBpmnFiles(Pageable pageable) {
        log.debug("REST request to get a page of BpmnFiles");
        Page<BpmnFilesDTO> page = bpmnFilesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bpmn-files/:id} : get the "id" bpmnFiles.
     *
     * @param id the id of the bpmnFilesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bpmnFilesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bpmn-files/{id}")
    public ResponseEntity<BpmnFilesDTO> getBpmnFiles(@PathVariable Long id) {
        log.debug("REST request to get BpmnFiles : {}", id);
        Optional<BpmnFilesDTO> bpmnFilesDTO = bpmnFilesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bpmnFilesDTO);
    }

    /**
     * {@code DELETE  /bpmn-files/:id} : delete the "id" bpmnFiles.
     *
     * @param id the id of the bpmnFilesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bpmn-files/{id}")
    public ResponseEntity<Void> deleteBpmnFiles(@PathVariable Long id) {
        log.debug("REST request to delete BpmnFiles : {}", id);
        bpmnFilesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
