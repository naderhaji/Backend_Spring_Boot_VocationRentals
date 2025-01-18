package com.hajji.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hajji.model.User;
import com.hajji.model.Vocation;
import com.hajji.repository.VocationRepository;

@Service
public class VocationServiceImplentation implements VocationService {

    @Autowired
    private VocationRepository vocationRepository;
    
    

    @Override
    public Vocation findVocationById(Long id) throws Exception {
        Optional<Vocation> opt = vocationRepository.findById(id);

        if(opt.isPresent()) {
            return opt.get();
        } else {
            throw new Exception("Vocation not found with id "+ id);
        }
    }

    @Override
    public void deleteVocation(Long id) throws Exception {
        findVocationById(id);

        vocationRepository.deleteById(id);
    }

    @Override
    public Vocation updateVocation(Vocation vocation, Long id) throws Exception {
        Vocation oldVocation = findVocationById(id);
        
        if(vocation.getTitle() != null) {
            oldVocation.setTitle(vocation.getTitle());
        }
        if (vocation.getImage() != null) {
            oldVocation.setImage(vocation.getImage());
        }
        if(vocation.getDescription() != null) {
            oldVocation.setDescription(vocation.getDescription());
        }

        return vocationRepository.save(oldVocation);
    }

    @Override
    public List<Vocation> findAllVocations() {
        return vocationRepository.findAll();
    }

    @Override
    public Vocation likeVocation(Long vocationId, User user) throws Exception {
        Vocation vocation = findVocationById(vocationId);

        if(vocation.getLikes().contains(user.getId())) {
            vocation.getLikes().remove(user.getId());
        } else {
            vocation.getLikes().add(user.getId());
        }
        return null;
    }

    @Override
    public Vocation createVocation(Vocation vocation, User user) {
            Vocation createdVocation = new Vocation();
            createdVocation.setTitle(vocation.getTitle());
            createdVocation.setImage(vocation.getImage());
            createdVocation.setDescription(vocation.getDescription());
            createdVocation.setUser(user);
            createdVocation.setCreatedAt(LocalDateTime.now());
            return vocationRepository.save(createdVocation);
    }

}
