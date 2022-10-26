package com.matthew.fakeInsta.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matthew.fakeInsta.models.Picture;
import com.matthew.fakeInsta.models.User;

@Repository
public interface PictureRepository extends CrudRepository<Picture, Long>{
	List<Picture> findAllByUser(User user);
}
