package cp ;
import modelling.*;
import java.util.*;


public interface ValueHeuristic{

    List<Object> ordering(Variable variable, Set<Object> domain);
}