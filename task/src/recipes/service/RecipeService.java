package recipes.service;

import org.springframework.stereotype.Service;
import recipes.dao.IdResponse;
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

    public IdResponse addRecipe(Recipe recipe, String username) {
        recipe.setDate(LocalDateTime.now());
        recipe.setCreator(username);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new IdResponse(savedRecipe.getId());
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public Boolean deleteRecipe(Long id, String username) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            if (username.equals(recipe.getCreator())) {
                recipeRepository.deleteById(id);
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        }
        return false;
    }

    public Boolean updateRecipe(Long id, Recipe recipe, String username) throws IllegalArgumentException {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            Recipe recipe1 = recipeOptional.get();
            if (username.equals(recipe1.getCreator())) {
                recipe.setId(id);
                recipe.setDate(LocalDateTime.now());
                recipe.setCreator(recipe1.getCreator());
                recipeRepository.save(recipe);
                return true;
            } else {
                throw new IllegalArgumentException();
            }
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
