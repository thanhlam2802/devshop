package javafive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import javafive.entity.Category;

@Service
public interface CategoryService {
	 	List<Category> getAllCategories();
	    
	    // 2. Lấy danh mục theo ID
	    Optional<Category> getCategoryById(Integer categoryId);
	    
	    // 3. Thêm mới danh mục
	    Category createCategory(Category category);
	    
	    // 4. Cập nhật danh mục
	    Category updateCategory(Integer categoryId, Category category);
	    
	    // 5. Xóa danh mục theo ID
	    void deleteCategory(Integer categoryId);

	    // 6. Lấy danh sách danh mục cha (các danh mục không có parent)
	    List<Category> getParentCategories();
	    
	    // 7. Lấy danh sách danh mục con theo ID danh mục cha
	    List<Category> getSubCategories(Integer parentId);
	    
	    // 8. Lấy danh mục cha của một danh mục con (nếu có)
	    Optional<Category> getParentCategory(Integer categoryId);
}
