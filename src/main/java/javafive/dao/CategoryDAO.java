package javafive.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javafive.entity.Category;



public interface CategoryDAO extends JpaRepository<Category, Integer>{
	List<Category> findByParentCategoryIsNull();
	@Query("SELECT c FROM Category c WHERE c.parentCategory.category_id = :parentId")
	List<Category> findSubCategories(@Param("parentId") Integer parentId);



}
