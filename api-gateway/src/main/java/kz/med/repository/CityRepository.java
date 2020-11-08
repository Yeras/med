package kz.med.repository;


import kz.med.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> findAllByCountryIdOrderByName(int countryId);

    City findById(int cityId);

}