package com.exam;

import com.exam.model.*;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injecter l'encodeur de mot de passe

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		
	}

    /*
<<<<<<< HEAD
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
=======
    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User rootAdmin = new User();
            rootAdmin.setActive(true);
            rootAdmin.setFirstName("Super");
            rootAdmin.setLastName("Admin");
            rootAdmin.setEmail("admin@example.com");
            rootAdmin.setPassword(passwordEncoder.encode("root")); // Hasher le mot de passe
            rootAdmin.setRole(UserRole.ADMIN);
            rootAdmin.setUsername("admin");

            userRepository.save(rootAdmin); // Utiliser save() au lieu de addUser()
            System.out.println("Admin utilisateur créé : admin / root");
        } else {
            System.out.println("L'utilisateur admin existe déjà !");
        }
>>>>>>> branch 'master' of https://github.com/Baraka-AHMED/testRepoSpring.git
    }
<<<<<<< HEAD

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
         
=======
>>>>>>> branch 'master' of https://github.com/Baraka-AHMED/testRepoSpring.git
*/
}
