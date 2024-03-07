package dat3.recipes.api;

import dat3.recipes.dto.CategoryDTO;
import dat3.recipes.entity.Category;
import dat3.recipes.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<String> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public Category addCategory(@RequestBody CategoryDTO request) {
        return categoryService.addCategory(request);
    }
}
