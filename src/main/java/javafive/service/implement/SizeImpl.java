package javafive.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafive.dao.SizeDAO;
import javafive.entity.Size;
import javafive.service.SizeService;
@Service
public class SizeImpl implements SizeService{
	@Autowired
	SizeDAO daos;
	@Override
	public Optional<Size> findByName(String name) {
		// TODO Auto-generated method stub
		return daos.findBySize(name);
	}

}
