package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.IdResponse;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public IdResponse addRecipe(Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new IdResponse(savedRecipe.getId());
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public Boolean deleteRecipe(Long id) {
        boolean exists = recipeRepository.existsById(id);
        if (exists) {
            recipeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
