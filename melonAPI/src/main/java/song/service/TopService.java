package song.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import song.entity.Top;
import song.repository.TopRepository;

@Service
public class TopService {
	private final TopRepository topRepository;

	@Autowired
	public TopService(TopRepository topRepository) {
		this.topRepository = topRepository;
	}

	public List<Top> getAll(){
		return topRepository.findAll();
	}
	public List<Top> getId(int id){
		return topRepository.findById(id);
	}
}
