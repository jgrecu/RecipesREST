package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.IdResponse;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable @Min(1) Long id) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipe(id);
        return optionalRecipe.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/new")
    public IdResponse addRecipe(@RequestBody @Valid Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        Boolean deletedRecipe = recipeService.deleteRecipe(id);
        if (deletedRecipe) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
