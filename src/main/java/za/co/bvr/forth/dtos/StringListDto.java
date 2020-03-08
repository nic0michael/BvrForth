package za.co.bvr.forth.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicm
 */
public class StringListDto implements Dto{
    
    private String guid;
    private String description;
    
    List <String> strings=new ArrayList<>();

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

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
}
