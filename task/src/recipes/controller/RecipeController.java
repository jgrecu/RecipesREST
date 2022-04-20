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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @RequestBody @Valid Recipe recipe) {
        Boolean updatedRecipe = recipeService.updateRecipe(id, recipe);
        if (updatedRecipe) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(name = "category", required = false) String category,
                                                     @RequestParam(name = "name", required = false) String name) {

        if ((category == null && name == null) || (category != null && name != null)) {
            return ResponseEntity.badRequest().build();
        }

        if (category != null) {
            List<Recipe> recipeList = recipeService.searchRecipesByCategory(category);
            return new ResponseEntity<>(recipeList, HttpStatus.OK);
        } else {
            List<Recipe> recipeList = recipeService.searchRecipesByName(name);
            return new ResponseEntity<>(recipeList, HttpStatus.OK);
        }
    }

}
