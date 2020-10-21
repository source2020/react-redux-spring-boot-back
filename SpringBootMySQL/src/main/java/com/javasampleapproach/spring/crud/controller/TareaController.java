package com.javasampleapproach.spring.crud.controller;

import com.javasampleapproach.spring.crud.model.Tarea;
import com.javasampleapproach.spring.crud.repo.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TareaController {

	@Autowired
	TareaRepository tareaRepository;

	@GetMapping("/tareas")
	public List<Tarea> getAllTareas() {
		System.out.println("Get all Tareas...");

		List<Tarea> list = new ArrayList<>();
		Iterable<Tarea> customers = tareaRepository.findAll();

		customers.forEach(list::add);
		return list;
	}

	@PostMapping("/tareas/create")
	public Tarea createTarea(@Valid @RequestBody Tarea tarea) {
		System.out.println("Create Tarea: " + tarea.getTitle() + "...");

		return tareaRepository.save(tarea);
	}

	@GetMapping("/tareas/{id}")
	public ResponseEntity<Tarea> getTarea(@PathVariable("id") Long id) {
		System.out.println("Get Tarea by id...");

		Optional<Tarea> tareaData = tareaRepository.findById(id);
		if (tareaData.isPresent()) {
			return new ResponseEntity<>(tareaData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/tareas/{id}")
	public ResponseEntity<Tarea> updateTarea(@PathVariable("id") Long id, @RequestBody Tarea tarea) {
		System.out.println("Update Tarea with ID = " + id + "...");

		Optional<Tarea> tareaData = tareaRepository.findById(id);
		if (tareaData.isPresent()) {
			Tarea savedTarea = tareaData.get();
			savedTarea.setTitle(tarea.getTitle());
			savedTarea.setAuthor(tarea.getAuthor());
			savedTarea.setDescription(tarea.getDescription());
			savedTarea.setPublished(tarea.getPublished());

			Tarea updatedTarea = tareaRepository.save(savedTarea);
			return new ResponseEntity<>(updatedTarea, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tareas/{id}")
	public ResponseEntity<String> deleteTarea(@PathVariable("id") Long id) {
		System.out.println("Delete Tarea with ID = " + id + "...");

		try {
			tareaRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("Tarea has been deleted!", HttpStatus.OK);
	}
}
