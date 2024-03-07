package dat3.recipes.service;

import dat3.recipes.dto.RecipeDTO;
import dat3.recipes.entity.Category;
import dat3.recipes.entity.Recipe;
import dat3.recipes.repository.CategoryRepository;
import dat3.recipes.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<RecipeDTO> getAllRecipes(String category) {
        List<Recipe> recipes = category == null ? recipeRepository.findAll() : recipeRepository.findByCategoryName(category);
        List<RecipeDTO> recipeResponses = recipes.stream().map((r) -> new RecipeDTO(r,false)).toList();
        return recipeResponses;
    }

    public RecipeDTO getRecipeById(int idInt) {
        Recipe recipe = recipeRepository.findById(idInt).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        return new RecipeDTO(recipe,false);
    }

    public RecipeDTO addRecipe(RecipeDTO request) {
        if (request.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot provide the id for a new recipe");
        }
        Category category = categoryRepository.findByName(request.getCategory()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Only existing categories are allowed"));
        Recipe newRecipe = new Recipe();
        updateRecipe(newRecipe, request, category);
        recipeRepository.save(newRecipe);
        return new RecipeDTO(newRecipe,false);
    }

    private void updateRecipe(Recipe original, RecipeDTO r, Category category) {
        original.setName(r.getName());
        original.setInstructions(r.getInstructions());
        original.setIngredients(r.getIngredients());
        original.setThumb(r.getThumb());
        original.setYouTube(r.getYouTube());
        original.setSource(r.getSource());
        original.setCategory(category);
    }

    public RecipeDTO editRecipe(RecipeDTO request, int id) {
        if (request.getId() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot change the id of an existing recipe");
        }
        Category category = categoryRepository.findByName(request.getCategory()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Only existing categories are allowed"));

        Recipe recipeToEdit = recipeRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        updateRecipe(recipeToEdit,request, category);
        recipeRepository.save(recipeToEdit);
        return new RecipeDTO(recipeToEdit,false);
    }

    public ResponseEntity deleteRecipe(int id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        recipeRepository.delete(recipe);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}

