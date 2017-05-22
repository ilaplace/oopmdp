import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.OOVariableKey;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ilaplace on 5/18/17.
 */
public class GState implements MutableOOState{

    public float[] p_values = new float[Domain.DOF];
    public float[] quality = new float[2];
    public Agent agent;

    public GState(Agent agent) {
        this.agent = agent;
    }

    @Override
    public Object get(Object variableKey) {
        return OOStateUtilities.get(this, variableKey);
    }

    @Override
    public MutableState set(Object variableKey, Object value) {
        throw new NotImplementedException();
//         for (int i = 0; i <Agent.p_values.length ; i++) {
//                    if (variableKey.toString().equals(Domain.variables[i]))
//                        this.p_values[i] = new Float(value.toString());
//                }
//                return this;
    }

    @Override
    public ObjectInstance object(String oname) {
        throw new NotImplementedException();
    }

    @Override
    public MutableOOState removeObject(String oname) {
        throw new NotImplementedException();
    }

    @Override
    public MutableOOState renameObject(String objectName, String newName) {
        throw new NotImplementedException();
    }

    @Override
    public MutableOOState addObject(ObjectInstance o) {
        throw new NotImplementedException();
    }

    @Override
    public List<ObjectInstance> objectsOfClass(String oclass) {
        return Arrays.<ObjectInstance>asList(agent);
    }

    @Override
    public int numObjects() {
      return 1;
    }

    @Override
    public List<ObjectInstance> objects() {
        List<ObjectInstance> obs = new ArrayList<ObjectInstance>(1);
        obs.add(agent);
        return obs;
    }

    @Override
    public List<Object> variableKeys() {
        return OOStateUtilities.flatStateKeys(this);
    }

    @Override
    public State copy() {
        return new GState(agent);
    }
}
