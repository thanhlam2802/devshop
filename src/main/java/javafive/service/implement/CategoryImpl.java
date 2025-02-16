package javafive.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.CategoryDAO;
import javafive.entity.Category;
import javafive.service.CategoryService;

@Service
public class CategoryImpl implements CategoryService{

	 @Autowired
	 CategoryDAO daoc;
	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Category> getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Category createCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category updateCategory(Integer categoryId, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Category> getParentCategories() {
		
		return daoc.findByParentCategoryIsNull();
	}

	@Override
	public List<Category> getSubCategories(Integer parentId) {
		
		return daoc.findSubCategories(parentId);
	}

	@Override
	public Optional<Category> getParentCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
