package za.co.bvr.forth.dictionary;

import java.util.UUID;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author nickm 
 */
@Data
@ToString
public class Verb {
    private final String name;
    private final String definition;
    private final String compiledDefinition;
    private String description;
    private String uuid;

    public Verb(String name, String definition, String compiledDefinition,String description) {
        this.compiledDefinition = compiledDefinition;
        this.definition = definition;
        this.description=description;
        this.name = name;
        uuid = UUID.randomUUID().toString();
    }
    
    public Verb(String name, String definition, String compiledDefinition,String uuid,String description) {
        this.compiledDefinition = compiledDefinition;
        this.definition = definition;
        this.description=description;
        this.name = name;
        this.uuid=uuid;
    }

    public String getName() {
        return name;
    }

    public String getDefinition() {
        return definition;
    }

    public String getCompiledDefinition() {
        return compiledDefinition;
    }

    
    public String getDescription() {
        return description;
    }
    
    
    public void setDescription(String description) {
        this.description= description;
    }
    
    public String getUuid() {
        return uuid;
    }
        
    public boolean equals(Verb verb){
        return this.name.equals(verb.getName());
    }
    
    public boolean equals(String name){
        return this.name.equals(name);
    }
    
    public boolean equals(String name, String definition, String compiledDefinition) {
        return this.name.equals(name)&& this.definition.equals(definition) && this.compiledDefinition.equals(compiledDefinition);
    }
    
    public boolean guidEquals(String uuid){
        return this.uuid.equals(uuid);
    }
}
