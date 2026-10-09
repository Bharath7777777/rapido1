package com.alpha.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.customer.entity.Coordinates;

public interface Coordinaterepository extends JpaRepository<Coordinates, Integer>{

}
