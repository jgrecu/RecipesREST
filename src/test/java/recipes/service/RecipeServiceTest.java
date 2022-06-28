package recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import recipes.dao.IdResponse;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecipeServiceTest {

    @MockBean
    private RecipeRepository repository;

    @Autowired
    private RecipeService recipeService;

    private Recipe recipe;


    @BeforeEach
    void setUp() {
        recipe = new Recipe(1L, "Fresh Mint Tea", "Light, aromatic and refreshing beverage, ...",
                "beverage",
                LocalDateTime.now(),
                "testuser",
                List.of("boiled water", "honey", "fresh mint leaves"),
                List.of("Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves",
                        "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"));
        when(repository.save(any(Recipe.class))).thenReturn(recipe);
        when(repository.findById(1L)).thenReturn(Optional.of(recipe));
    }

    @Test
    void getRecipeShouldReturnValid() {
        Optional<Recipe> recipe = recipeService.getRecipe(1L);
        assertTrue(recipe.isPresent());
        assertEquals(1, recipe.get().getId());
        assertEquals("Fresh Mint Tea", recipe.get().getName());
        assertEquals("beverage", recipe.get().getCategory());
    }

    @Test
    void addRecipeShouldReturnValid() {
        recipe.setId(2L);
        IdResponse idResponse = recipeService.addRecipe(recipe, "test");
        assertEquals(2, idResponse.getId());
    }

    @Test
    void deleteRecipeShouldReturnTrue() {
        Boolean deleteRecipe = recipeService.deleteRecipe(1L, "testuser");
        assertTrue(deleteRecipe);
    }

    @Test
    void deleteRecipeNotExistReturnFalse() {
        Boolean deleteRecipe = recipeService.deleteRecipe(2L, "testuser");
        assertFalse(deleteRecipe);
    }

    @Test
    void deleteValidRecipeInvalidUserShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> recipeService.deleteRecipe(1L, "jeremy"));
    }
}