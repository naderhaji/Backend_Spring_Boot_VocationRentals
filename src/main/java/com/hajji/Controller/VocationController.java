package com.hajji.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hajji.model.User;
import com.hajji.model.Vocation;
import com.hajji.repository.UserRepository;
import com.hajji.service.UserService;
import com.hajji.service.VocationService;

@RestController
@RequestMapping("/api/vocation")
public class VocationController {
    

    @Autowired
    private VocationService vocationService;
    

    @Autowired
    private UserService userService;


    @PostMapping()
    public Vocation createVocation(@RequestBody Vocation vocation, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwt(jwt);
        Vocation vocationCreated = vocationService.createVocation(vocation, user);
        return vocationCreated;
        
    }

    @GetMapping()
    public List<Vocation> findAllVocations(){
        List<Vocation> vocationList = vocationService.findAllVocations();
        return vocationList;
    }

    @DeleteMapping("/{vocationId}")
    public String deleteVocation(@PathVariable long vocationId) throws Exception{
        vocationService.deleteVocation(vocationId);
        return "Vocation deleted successfully";
    }

    @PutMapping("/{vocationId}")
    public Vocation updateVocation(@PathVariable Long id, @RequestBody Vocation vocation) throws Exception{
        Vocation vocationUpdated = vocationService.updateVocation(vocation, id);
        return vocationUpdated;
    }

    @PutMapping("/{id}/like")
    public Vocation likeVocation(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{

        User user = userService.findUserByJwt(jwt);
        Vocation vocationUpdated = vocationService.likeVocation(id, user);
        return vocationUpdated;
    }



}
