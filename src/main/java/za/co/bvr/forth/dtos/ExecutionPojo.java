package za.co.bvr.forth.dtos;

import lombok.Data;
import za.co.bvr.forth.enums.ExecutionType;

/**
 *
 * @author nicm
 */
@Data
public class ExecutionPojo {
    
    private ExecutionType executionType;
    private String lineItem;
    
}
