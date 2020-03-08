package za.co.bvr.forth.dtos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nicm
 */
public class MapDto implements Dto{
    
    private String guid;
    private String description;
    
    private Map<String,String> map=new HashMap<>();

    @Override
    public String getGuid() {
        return guid;
    }

    @Override
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void put(String key,String value){
        map.put(key, value);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    
}
