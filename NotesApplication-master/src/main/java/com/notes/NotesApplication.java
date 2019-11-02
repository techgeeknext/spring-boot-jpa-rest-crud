package com.notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.notes.model.Note;
import com.notes.model.User;
import com.notes.repository.NotesRepository;
import com.notes.repository.UserRepository;
@SpringBootApplication
@EnableJpaAuditing
public class NotesApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private NotesRepository noteRepo;
	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}
	@Override
    public void run(String... arg0) throws Exception {
		User user = new User();
		user.setUserMail("techgeeknext@gmail.com");
		user.setUserPass("12345678jhg");
		userRepo.save(user);
		System.out.println("-----user generated---"+user.getUserId());
	}
}
