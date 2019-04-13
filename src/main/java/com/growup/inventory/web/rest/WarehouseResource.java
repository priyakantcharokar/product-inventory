package com.growup.inventory.web.rest;
import com.growup.inventory.domain.Warehouse;
import com.growup.inventory.repository.WarehouseRepository;
import com.growup.inventory.web.rest.errors.BadRequestAlertException;
import com.growup.inventory.web.rest.util.HeaderUtil;
import com.growup.inventory.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Warehouse.
 */
@RestController
@RequestMapping("/api")
public class WarehouseResource {

    private final Logger log = LoggerFactory.getLogger(WarehouseResource.class);

    private static final String ENTITY_NAME = "warehouse";

    private final WarehouseRepository warehouseRepository;

    public WarehouseResource(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * POST  /warehouses : Create a new warehouse.
     *
     * @param warehouse the warehouse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new warehouse, or with status 400 (Bad Request) if the warehouse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/warehouses")
    public ResponseEntity<Warehouse> createWarehouse(@Valid @RequestBody Warehouse warehouse) throws URISyntaxException {
        log.debug("REST request to save Warehouse : {}", warehouse);
        if (warehouse.getId() != null) {
            throw new BadRequestAlertException("A new warehouse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Warehouse result = warehouseRepository.save(warehouse);
        return ResponseEntity.created(new URI("/api/warehouses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /warehouses : Updates an existing warehouse.
     *
     * @param warehouse the warehouse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated warehouse,
     * or with status 400 (Bad Request) if the warehouse is not valid,
     * or with status 500 (Internal Server Error) if the warehouse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/warehouses")
    public ResponseEntity<Warehouse> updateWarehouse(@Valid @RequestBody Warehouse warehouse) throws URISyntaxException {
        log.debug("REST request to update Warehouse : {}", warehouse);
        if (warehouse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Warehouse result = warehouseRepository.save(warehouse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, warehouse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /warehouses : get all the warehouses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of warehouses in body
     */
    @GetMapping("/warehouses")
    public ResponseEntity<List<Warehouse>> getAllWarehouses(Pageable pageable) {
        log.debug("REST request to get a page of Warehouses");
        Page<Warehouse> page = warehouseRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/warehouses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /warehouses/:id : get the "id" warehouse.
     *
     * @param id the id of the warehouse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the warehouse, or with status 404 (Not Found)
     */
    @GetMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable Long id) {
        log.debug("REST request to get Warehouse : {}", id);
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(warehouse);
    }

    /**
     * DELETE  /warehouses/:id : delete the "id" warehouse.
     *
     * @param id the id of the warehouse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/warehouses/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        log.debug("REST request to delete Warehouse : {}", id);
        warehouseRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
