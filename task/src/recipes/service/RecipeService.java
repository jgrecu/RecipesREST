package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.Recipe;

@Service
public class RecipeService {
    private Recipe recipe;

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
