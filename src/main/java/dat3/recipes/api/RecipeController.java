package dat3.recipes.api;

import dat3.recipes.dto.RecipeDTO;
import dat3.recipes.entity.Recipe;
import dat3.recipes.repository.RecipeRepository;
import dat3.recipes.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

@GetMapping
public List<RecipeDTO> getAllRecipes(@RequestParam(required = false) String category) {
    if(category != null) {
        System.out.println("Category: " + category);
    }
    return recipeService.getAllRecipes(category);
}

@GetMapping(path ="/{id}")
public RecipeDTO getRecipeById(@PathVariable int id) {
    return recipeService.getRecipeById(id);
}

@PostMapping
public RecipeDTO addRecipe(@RequestBody RecipeDTO request) {
    return recipeService.addRecipe(request);
}

@PutMapping(path = "/{id}")
public RecipeDTO addRecipe(@RequestBody RecipeDTO request,@PathVariable int id) {
    return recipeService.editRecipe(request,id);
}

@DeleteMapping(path = "/{id}")
public ResponseEntity deleteRecipe(@PathVariable int id) {
    return recipeService.deleteRecipe(id);
}


}



