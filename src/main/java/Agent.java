import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by semih on 14.04.2017.
 */
//deneme commitd
public class Agent implements ObjectInstance, MutableState, MutableOOState{

    private static List<String> keys = Arrays.asList(Domain.variables);
    public String name = "agent";

    public float[] p_values = new float[Domain.DOF];
    public  float[] quality = new float[2];

    public Agent() {
    }

    public Agent(float[] values, float[] quality) {
        this.p_values = values;
        this.quality = quality;
    }

    public Agent(float[] values, String name) {
            this.p_values = values;
             this.name = name;
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
    public List<Object> variableKeys() {
        List<Object> keys_obje = new ArrayList<Object>(Domain.DOF);
        for (int i = 0; i < Domain.DOF; i++) {
            keys_obje.add(i, (Object) keys.get(i));
        }
        return keys_obje;
    }

    @Override
    public Object get(Object variableKey) {
        for (int i = 0; i < this.p_values.length; i++) {
            if (variableKey.equals(Domain.variables[i]))
                return this.p_values[i];
        }
        throw new UnknownKeyException(variableKey);
    }
    @Override
    public Agent copy(){
        return new Agent(p_values,quality);
}
    @Override
    public ObjectInstance copyWithName(String objectName) {
        return new Agent(p_values, objectName);
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
    public ObjectInstance object(String s) {
        throw new NotImplementedException();
    }

    @Override
    public List<ObjectInstance> objects() {
        throw new NotImplementedException();
    }

    @Override
    public List<ObjectInstance> objectsOfClass(String s) {
        return Arrays.<ObjectInstance>asList(this);
    }

    @Override
    public int numObjects() {
        throw new NotImplementedException();

    }

    @Override
    public MutableOOState renameObject(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public MutableOOState addObject(ObjectInstance objectInstance) {
        return null;
    }

    @Override
    public MutableOOState removeObject(String s) {
        return null;
    }

    public static void main(String[] args) {

        Object obj = 2.00002;
        Float as = new Float(obj.toString());
        System.out.print(as);
        Agent ag = new Agent();


    }
}
