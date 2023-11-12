package za.co.bvr.forth.process;



import za.co.bvr.forth.processor.implemented.Inputprocessor;

public class ProcessInput {
  Inputprocessor processor =new Inputprocessor();

  public String process(String input) throws Exception {
    return processor.process(input);
  }
}
