package dat3.recipes.service;

import dat3.recipes.dto.CategoryDTO;
import dat3.recipes.entity.Category;
import dat3.recipes.repository.CategoryRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        //Convert from list of Categories to DTO-type, list of Strings
        return categories.stream().map((c)->new String(c.getName())).toList();
    }

    public Category addCategory(CategoryDTO request) {
        String formattedName = formatCategoryName(request.getName());
        Category newCategory = new Category(formattedName);
        categoryRepository.save(newCategory);
        return newCategory;
    }

    public String formatCategoryName(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
