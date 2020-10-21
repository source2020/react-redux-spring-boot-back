package com.javasampleapproach.spring.crud.repo;

import com.javasampleapproach.spring.crud.model.Tarea;
import org.springframework.data.repository.CrudRepository;


public interface TareaRepository extends CrudRepository<Tarea, Long> {

}
