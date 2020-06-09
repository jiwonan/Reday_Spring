package kr.hs.emirim.jiwon.reday.reday;

import org.springframework.data.repository.CrudRepository;

public interface CountriesRepository extends CrudRepository<Countries, Long> {
	public Countries findByCountry(String country);
}
