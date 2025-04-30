package com.taco_cloud;

import com.taco_cloud.data.Ingredient;
import com.taco_cloud.data.Taco;
import com.taco_cloud.repository.IngredientRepository;
import com.taco_cloud.repository.TacoRepository;
import com.taco_cloud.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository repo, UserRepository userRepo, PasswordEncoder encoder, TacoRepository tacoRepo) {
        return args -> {
            Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
            Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
            Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
            Ingredient carnitas = new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN);
            Ingredient dicedTomatoes = new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
            Ingredient lettuce = new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES);
            Ingredient cheddar = new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
            Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE);
            Ingredient salsa = new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE);
            Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE);
            repo.saveAll(List.of(flourTortilla, cornTortilla, groundBeef, carnitas, dicedTomatoes, lettuce, cheddar, jack, salsa, sourCream));

            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(List.of(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
            tacoRepo.save(taco1);

            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(List.of(cornTortilla, groundBeef, cheddar, jack, sourCream));
            tacoRepo.save(taco2);

            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(List.of(flourTortilla, cornTortilla, dicedTomatoes, lettuce, salsa));
            tacoRepo.save(taco3);
        };
    }
}
