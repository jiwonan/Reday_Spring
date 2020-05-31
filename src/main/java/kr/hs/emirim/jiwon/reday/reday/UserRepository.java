package kr.hs.emirim.jiwon.reday.reday;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, Long> {
	public User findByUserName(String username);
	public User findByEmail(String email);
}
