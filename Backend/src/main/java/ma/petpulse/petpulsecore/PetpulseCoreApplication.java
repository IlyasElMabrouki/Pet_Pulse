package ma.petpulse.petpulsecore;

import ma.petpulse.petpulsecore.dao.entities.*;
import ma.petpulse.petpulsecore.dao.repositories.*;
import ma.petpulse.petpulsecore.enumerations.Role;
import ma.petpulse.petpulsecore.enumerations.Specie;
import org.springframework.boot.CommandLineRunner;
import ma.petpulse.petpulsecore.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class PetpulseCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetpulseCoreApplication.class, args);
    }

    //@Bean
    /*public CommandLineRunner start(UserRepository userRepository, PetRepository petRepository, ReportRepository reportRepository, AdoptionApplicationRepository adoptionApplicationRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            // Create some User entities
          /*  User user1 = new User();
            user1.setFirstName("user1");
            user1.setLastName("user1");
            user1.setPassword(passwordEncoder.encode("password1"));
            user1.setEmail("user1@example.com");
            user1.setRole(Role.ROLE_PET_OWNER);

            User user2 = new User();
            user2.setFirstName("user2");
            user2.setLastName("user2");
            user2.setPassword(passwordEncoder.encode("password2"));
            user2.setEmail("user2@example.com");
            user2.setRole(Role.ROLE_PET_OWNER);

            userRepository.save(user1);
            userRepository.save(user2);

            // Create some Pet entities
            Pet pet1 = new Pet();
            pet1.setName("Dog");
            pet1.setAge(5);
            pet1.setOwner(user1);
            pet1.setImageURL("dog_image_url"); // Set imageURL
            pet1.setBreed("Bulldog"); // Set breed

            Pet pet2 = new Pet();
            pet2.setName("Cat");
            pet2.setAge(3);
            pet2.setOwner(user2);
            pet2.setImageURL("cat_image_url"); // Set imageURL
            pet2.setBreed("Persian"); // Set breed

            petRepository.save(pet1);
            petRepository.save(pet2);

            // Create some Report entities
            Report report1 = new Report();
            report1.setTitle("Lost dog");
            report1.setDescription("Lost in the park");
            report1.setUser(user1);
            report1.setPet(pet1);

            Report report2 = new Report();
            report2.setTitle("Found cat");
            report2.setDescription("Found near the supermarket");
            report2.setUser(user2);
            report2.setPet(pet2);

            reportRepository.save(report1);
            reportRepository.save(report2);

            // Create some AdoptionApplication entities
            AdoptionApplication application1 = new AdoptionApplication();
            application1.setReason("I have a big yard");
            application1.setNumberOfPets(0);
            application1.setUser(user1);
            application1.setReport(report1);

            AdoptionApplication application2 = new AdoptionApplication();
            application2.setReason("I love cats");
            application2.setNumberOfPets(1);
            application2.setUser(user2);
            application2.setReport(report2);

            adoptionApplicationRepository.save(application1);
            adoptionApplicationRepository.save(application2);

            User user1 = new User();
            user1.setFirstName("user1");
            user1.setLastName("user1");
            user1.setPassword(passwordEncoder.encode("password1"));
            user1.setEmail("user1@example.com");
            user1.setRole(Role.ROLE_PET_OWNER);
            userRepository.save(user1);

            Pet pet = new Pet();
            pet.setName("Dog");
            pet.setAge(5);
            pet.setSpecie(Specie.DOG);
            pet.setOwner(userRepository.findById(1L).get());
            pet.setBreed("Bulldog"); // Set breed
            petRepository.save(pet);
        };*/


        @Bean
        public CommandLineRunner start(UserRepository userRepository,
                                       PetRepository petRepository,
                                       PasswordEncoder passwordEncoder,
                                       PetImageRepository petImageRepository) {
            return (args) -> {

                User user1 = new User();
                user1.setFirstName("user1");
                user1.setLastName("user1");
                user1.setPassword(passwordEncoder.encode("password1"));
                user1.setEmail("user1@example.com");
                user1.setRole(Role.ROLE_PET_OWNER);
                userRepository.save(user1);

                User user2 = new User();
                user2.setFirstName("user2");
                user2.setLastName("user2");
                user2.setPassword(passwordEncoder.encode("password2"));
                user2.setEmail("user2@example.com");
                user2.setRole(Role.ROLE_PET_OWNER);
                userRepository.save(user2);


                /*for (int i=0;i<5;i++){
                    Pet pet = new Pet();
                    pet.setName("Dog" + i);
                    pet.setAge(5);
                    pet.setSpecie(Specie.DOG);
                    pet.setOwner(user1);
                    pet.setBreed("Bulldog"); // Set breed
                    petRepository.save(pet);

                    PetImage petImage1 = new PetImage();
                    petImage1.setUrl("dog_image_url1");
                    petImage1.setPet(pet);
                    petImageRepository.save(petImage1);

                    PetImage petImage2 = new PetImage();
                    petImage2.setUrl("dog_image_url2");
                    petImage2.setPet(pet);
                    petImageRepository.save(petImage2);
                }*/


            };
            //adoptionApplicationRepository.save(application2);*/
        };
}
