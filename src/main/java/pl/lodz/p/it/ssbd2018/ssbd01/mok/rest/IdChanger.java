/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;


import java.util.Map;

import java.util.TreeMap;
import java.util.UUID;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

/**
 *
 * @author michal
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class IdChanger {
    Map<String, Long> uuid;
    Map<String, Long> version;

    public IdChanger() {
        uuid = new TreeMap<>();
        version = new TreeMap<>();
    }
    
    public String addId(Long id) {
        String newId = UUID.randomUUID().toString().replace("-", "");
        uuid.put(newId, id);
        return newId;
    }
    
    public String addIdAndVersion(Long id, Long ver) {
        String newId = UUID.randomUUID().toString().replace("-", "");
        uuid.put(newId, id);
        version.put(newId, ver);
        return newId;
    }
    
    public Long getId(String oldId) {
        Long id = uuid.get(oldId);
        uuid.remove(oldId);
        return id;
    }
    
    public Long getVersion(String oldId) {
        Long id = version.get(oldId);
        version.remove(oldId);
        return id;
    }
    
    public Long getIdWithoutDelete(String oldId) {
        Long id = uuid.get(oldId);
        return id;
    }
    
    public boolean containsId(String oldId) {
        return uuid.containsKey(oldId);
    }
    
}
