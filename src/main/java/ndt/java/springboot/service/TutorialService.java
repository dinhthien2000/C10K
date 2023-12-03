package ndt.java.springboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import ndt.java.springboot.model.Tutorial;
import ndt.java.springboot.repository.TutorialRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * Flux : trả về nhiều giá trị (List, Map)
 * Mono: trả về 1 giá trị (String, Object)
 * 
 * */

@Service
@EnableCaching
public class TutorialService {
	  @Autowired
	  TutorialRepository tutorialRepository;

	  @Cacheable("tutorials")
	  public Flux<Tutorial> findAll() {
	    return tutorialRepository.findAll();
	  }
	  
	  @Cacheable("tutorials")
	  public Flux<Tutorial> findByTitleContaining(String title) {
	    return tutorialRepository.findByTitleContaining(title);
	  }

	  @Cacheable("tutorials")
	  public Mono<Tutorial> findById(int id) {
	    return tutorialRepository.findById(id);
	  }

	  public Mono<Tutorial> save(Tutorial tutorial) {
//		Mono<Tutorial> tempTutorial = null;
//		int random = (int)(Math.random() * 50 + 1);
//	
//		if (Integer.valueOf(tutorial.getId()) != null) {
//			tempTutorial = tutorialRepository.save(tutorial);
//		}else {
//			tutorial.setId(random);
//			tempTutorial = tutorialRepository.save(tutorial);
//		}
//	
//	    return tempTutorial;
	    
	    return tutorialRepository.save(tutorial);
	  }

	  public Mono<Tutorial> update(int id, Tutorial tutorial) {
	    return tutorialRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
	        .flatMap(optionalTutorial -> {
	          if (optionalTutorial.isPresent()) {
	            tutorial.setId(id);
	            return tutorialRepository.save(tutorial);
	          }

	          return Mono.empty();
	        });
	  }

	  public Mono<Void> deleteById(int id) {
	    return tutorialRepository.deleteById(id);
	  }

	  public Mono<Void> deleteAll() {
	    return tutorialRepository.deleteAll();
	  }

	  @Cacheable("published_tutorials")
	  public Flux<Tutorial> findByPublished(boolean isPublished) {
	    return tutorialRepository.findByPublished(isPublished);
	  }
}
