import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by semih on 14.04.2017.
 */
public class Agent implements ObjectInstance, MutableOOState{

    public static List<String> keys = Arrays.asList(Domain.variables);
    public String name = "agent";

    public float[] p_values = new float[Domain.DOF];
    public float[] quality = new float[2];

    private Agent() {
    }

    public Agent(float[] values, float[] quality) {
        this.p_values = values;
        this.quality = quality;
        Domain.quality = quality;
    }

    public Agent(float[] values,  float[] quality, String name) {
            this.p_values = values;
            this.quality = quality;
            this.name = name;
    }

    @Override
    public List<Object> variableKeys() {
        List<Object> keys_obje = new ArrayList<Object>(Domain.DOF);
        for (int i = 0; i < Domain.DOF; i++) {
            keys_obje.add(i, (Object)keys.get(i));
        }
        return keys_obje;
    }

    @Override
    public Object get(Object variableKey) {
        for (int i = 0; i < this.p_values.length; i++) {
            if (variableKey.equals(Domain.variables[i]))
                return this.p_values[i];
        }
        if (variableKey.equals(Domain.quality))
            return quality;
        throw new UnknownKeyException(variableKey);
    }
    @Override
    public MutableState set(Object variableKey, Object value) {
         for (int i = 0; i <this.p_values.length ; i++) {
                    if (variableKey.toString().equals(Domain.variables[i]))
                        this.p_values[i] = new Float(value.toString());
                }
                return this;
    }

    @Override
    public ObjectInstance copyWithName(String objectName) {
        return new Agent(p_values, quality,  objectName);
    }

    @Override
    public String className() {
        return Domain.CLASS_AGENT;
    }

    @Override
    public String name() {
        return name;
    }
    @Override
    public ObjectInstance object(String oname) {
        return this;
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
        return Arrays.<ObjectInstance>asList(this);
    }

    @Override
    public int numObjects() {
        return 1;
    }

    @Override
    public List<ObjectInstance> objects() {
        List<ObjectInstance> obs = new ArrayList<ObjectInstance>(1);
        obs.add(this);
        return obs;
    }

    @Override
    public State copy() {
        return new Agent();
    }
}
