package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.Recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RecipeService {
    private AtomicInteger atomicInteger = new AtomicInteger();
    private Map<Integer, Recipe> recipes = new HashMap<>();

    public Integer addRecipe(Recipe recipe) {
        int id = atomicInteger.addAndGet(1);
        recipes.put(id, recipe);
        return id;
    }

    public Optional<Recipe> getRecipe(Integer id) {
        if (recipes.containsKey(id)) {
            return Optional.of(recipes.get(id));
        } else {
            return Optional.empty();
        }

    }
}
