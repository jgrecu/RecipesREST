package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.dao.IdResponse;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public IdResponse addRecipe(@RequestBody @Valid Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        return recipeService.addRecipe(recipe, details.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        try {
            Boolean deletedRecipe = recipeService.deleteRecipe(id, details.getUsername());
            if (deletedRecipe) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id,
                                             @RequestBody @Valid Recipe recipe,
                                             @AuthenticationPrincipal UserDetails details) {
        try {
            Boolean updatedRecipe = recipeService.updateRecipe(id, recipe, details.getUsername());
            if (updatedRecipe) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(name = "category", required = false) String category,
                                                     @RequestParam(name = "name", required = false) String name) {

        if ((category == null && name == null) || (category != null && name != null)) {
            return ResponseEntity.badRequest().build();
        }

        List<Recipe> recipeList;

        if (category != null) {
            recipeList = recipeService.searchRecipesByCategory(category);
        } else {
            recipeList = recipeService.searchRecipesByName(name);
        }
        return ResponseEntity.ok().body(recipeList);
    }

}
