package com.hajji.service;



import java.util.List;

import com.hajji.model.User;
import com.hajji.model.Vocation;

public interface VocationService {

    public Vocation createVocation(Vocation vocation, User user);
    
    public Vocation findVocationById(Long id) throws Exception;
    
    public void deleteVocation(Long id) throws Exception;

    public Vocation updateVocation(Vocation vocation, Long id) throws Exception;

    public List<Vocation> findAllVocations();

    public Vocation likeVocation(Long vocationId, User user) throws Exception;

    

}
