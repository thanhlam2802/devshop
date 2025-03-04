package javafive.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.ColorDAO;
import javafive.entity.Color;
import javafive.service.ColorService;

@Service
public class ColorImpl implements ColorService{

	@Autowired
	ColorDAO colordao;
	
	

	@Override
	public Optional<Color> getColorById(Integer colorid) {
		
		return colordao.findById(colorid);
	}



	@Override
	public Optional<Color> findByName(String name) {
		// TODO Auto-generated method stub
		return colordao.findByName(name);
	}

}
