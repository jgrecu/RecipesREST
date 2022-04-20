package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.IdResponse;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public IdResponse addRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
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
        }
        return false;
    }

    public Boolean updateRecipe(Long id, Recipe recipe) {
        boolean exists = recipeRepository.existsById(id);
        if (exists) {
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now());
            recipeRepository.save(recipe);
            return true;
        }
        return false;
    }

    public List<Recipe> searchRecipesByName(String term) {
        return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(term);
    }

    public List<Recipe> searchRecipesByCategory(String term) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(term);
    }
}
